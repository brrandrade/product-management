package com.engyes.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.engyes.product.model.ProductEntity;
import com.engyes.product.service.CategoryServiceInterface;
import com.engyes.product.service.ProductServiceInterface;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;

/**
 * The Class ProductController 
 *
 * @author  Bruno Andrade
 */
@Controller
@SessionAttributes( "product" )
public class ProductController {

	/** The Constant SAVE_PRODUCT. */
	private static final String SAVE_PRODUCT = "saveProduct";
	
	/** The Constant STATUS_NOTFOUND. */
	private static final String STATUS_NOTFOUND = "notfound";
	
	/** The Constant EDIT. */
	private static final String EDIT = "edit";
	
	/** The Constant DELETION. */
	private static final String DELETION = "deletion";
	
	/** The Constant STATUS_UNSUCCESS. */
	private static final String STATUS_UNSUCCESS = "unsuccess";
	
	/** The Constant STATUS_SUCCESS. */
	private static final String STATUS_SUCCESS = "success";
	
	/** The Constant REDIRECT_INDEX. */
	private static final String REDIRECT_INDEX = "redirect:/";
	
	/** The Constant PAGE_PRODUCT_FORM. */
	private static final String PAGE_PRODUCT_FORM = "productForm";

	/** The product service. */
	@Autowired
	private ProductServiceInterface productService;
	
	/** The category service. */
	@Autowired
	private CategoryServiceInterface categoryService;

	/**
	 * Sets the allowed fields.
	 *
	 * @param dataBinder the new allowed fields
	 */
	@InitBinder
	public void setAllowedFields( WebDataBinder dataBinder ) {
		dataBinder.setDisallowedFields( "id" );
	}

	/**
	 * Inits the creation form.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping( value = "/product/new", method = RequestMethod.GET )
	public String initCreationForm( Model model ) {
		ProductEntity product = new ProductEntity();
		model.addAttribute( "categories", categoryService.findAllSorted() );
		model.addAttribute( "product", product );
		return PAGE_PRODUCT_FORM;
	}

	/**
	 * Process creation form.
	 *
	 * @param product the product
	 * @param bindingResult the binding result
	 * @param model the model
	 * @param redirectAttributes the redirect attributes
	 * @param status the status
	 * @return the string
	 */
	@RequestMapping( value = "/product/new", method = RequestMethod.POST )
	public String processCreationForm( @Valid ProductEntity product, BindingResult bindingResult, Model model,
			final RedirectAttributes redirectAttributes, SessionStatus status ) {

		if ( bindingResult.hasErrors() ) {
			model.addAttribute( "categories", categoryService.findAllSorted() );
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

	/**
	 * Inits the update form.
	 *
	 * @param prodId the prod id
	 * @param redirectAttributes the redirect attributes
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping( value = "/product/{prodId}/edit", method = RequestMethod.GET )
	public String initUpdateForm( @PathVariable( "prodId" ) long prodId,
			final RedirectAttributes redirectAttributes, Model model) {
		ProductEntity product = productService.findProduct( prodId );
		if ( product == null ) {
			redirectAttributes.addFlashAttribute( EDIT, STATUS_NOTFOUND );
			return REDIRECT_INDEX;
		}
		model.addAttribute( "categories", categoryService.findAllSorted() );
		model.addAttribute( "product", product );
		return PAGE_PRODUCT_FORM;
	}

	/**
	 * Process update form.
	 *
	 * @param product the product
	 * @param result the result
	 * @param model the model
	 * @param redirectAttributes the redirect attributes
	 * @param status the status
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping( value = "/product/{prodId}/edit", method = RequestMethod.POST )
	public String processUpdateForm(@ModelAttribute("product")  @Valid ProductEntity product, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, SessionStatus status, HttpServletRequest request ) {

		if ( result.hasErrors() ) {
			model.addAttribute( "categories", categoryService.findAllSorted() );
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

	/**
	 * Find all for data tables.
	 *
	 * @param request the request
	 * @return the datatables response
	 */
	@RequestMapping( value = "/ajax/products" )
	public @ResponseBody DatatablesResponse<ProductEntity> findAllForDataTables( HttpServletRequest request ) {
		DatatablesCriterias criterias = DatatablesCriterias.getFromRequest( request );
		DataSet<ProductEntity> products = productService.findProductsWithDatatablesCriterias( criterias );
		return DatatablesResponse.build( products, criterias );
	}

	/**
	 * Edits the remove product.
	 *
	 * @param prodId the prod id
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
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
