package com.engyes.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.engyes.product.model.Product;
import com.engyes.product.service.CategoryServiceInterface;
import com.engyes.product.service.ProductServiceInterface;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;

@Controller
@SessionAttributes( "product" )
public class ProductController {

	private static final String SAVE_PRODUCT = "saveProduct";
	private static final String STATUS_NOTFOUND = "notfound";
	private static final String EDIT = "edit";
	private static final String DELETION = "deletion";
	private static final String STATUS_UNSUCCESS = "unsuccess";
	private static final String STATUS_SUCCESS = "success";
	private static final String REDIRECT_INDEX = "redirect:/";
	private static final String PAGE_PRODUCT_FORM = "productForm";
	
	@Autowired
	ProductServiceInterface productService;
	@Autowired
	CategoryServiceInterface categoryService;

	@InitBinder
	public void setAllowedFields( WebDataBinder dataBinder ) {
		dataBinder.setDisallowedFields( "id" );
	}

	@RequestMapping( value = "/product/new", method = RequestMethod.GET )
	public String initCreationForm( Model model ) {
		Product product = new Product();
		model.addAttribute( "cats", categoryService.findAllSorted() );
		model.addAttribute( "product", product );
		return PAGE_PRODUCT_FORM;
	}

	@RequestMapping( value = "/product/new", method = RequestMethod.POST )
	public String processCreationForm( @Valid Product product, BindingResult bindingResult, Model model,
			final RedirectAttributes redirectAttributes, SessionStatus status ) {

		if ( bindingResult.hasErrors() ) {
			model.addAttribute( "cats", categoryService.findAllSorted() );
			return PAGE_PRODUCT_FORM;
		} else {
			if ( productService.saveProduct( product ) != null ) {
				redirectAttributes.addFlashAttribute( SAVE_PRODUCT, STATUS_SUCCESS );
			} else {
				redirectAttributes.addFlashAttribute( SAVE_PRODUCT, STATUS_UNSUCCESS );
			}
			status.setComplete();
			return REDIRECT_INDEX;
		}
	}

	@RequestMapping( value = "/product/{prodId}/edit", method = RequestMethod.GET )
	public String initUpdateForm( @PathVariable( "prodId" ) long prodId,
			final RedirectAttributes redirectAttributes, Model model) {
		Product product = productService.findProduct( prodId );
		if ( product == null ) {
			redirectAttributes.addFlashAttribute( EDIT, STATUS_NOTFOUND );
			return REDIRECT_INDEX;
		}
		model.addAttribute( "cats", categoryService.findAllSorted() );
		model.addAttribute( "product", product );
		return PAGE_PRODUCT_FORM;
	}

	@RequestMapping( value = "/product/{prodId}/edit", method = RequestMethod.POST )
	public String processUpdateForm( @Valid Product product, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, SessionStatus status ) {
		if ( result.hasErrors() ) {
			model.addAttribute( "cats", categoryService.findAllSorted() );
			return PAGE_PRODUCT_FORM;
		} else {
			if ( !product.isNew() && productService.editProduct( product ) != null ) {
				redirectAttributes.addFlashAttribute( EDIT, STATUS_SUCCESS );
			} else {
				redirectAttributes.addFlashAttribute( EDIT, STATUS_UNSUCCESS );
			}
			status.setComplete();

			return REDIRECT_INDEX;
		}
	}

	@RequestMapping( value = "/ajax/products" )
	public @ResponseBody DatatablesResponse<Product> findAllForDataTables( HttpServletRequest request ) {
		DatatablesCriterias criterias = DatatablesCriterias.getFromRequest( request );
		DataSet<Product> products = productService.findProductsWithDatatablesCriterias( criterias );
		return DatatablesResponse.build( products, criterias );
	}

	@RequestMapping( value = "/product/{prodId}/delete", method = RequestMethod.GET )
	public String editRemoveProduct( @PathVariable( "prodId" ) Long prodId,
			final RedirectAttributes redirectAttributes) {
		if ( productService.deleteProduct( prodId ) ) {
			redirectAttributes.addFlashAttribute( DELETION, STATUS_SUCCESS );
		} else {
			redirectAttributes.addFlashAttribute( DELETION, STATUS_UNSUCCESS );
		}

		return REDIRECT_INDEX;
	}

}
