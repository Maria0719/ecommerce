package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.mapper.ProductCategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductCategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryResponse> getProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        if (productCategories.isEmpty()) return List.of();
        return ProductCategoryMapper.modelToProductCategoryResponseList(productCategories);
    }

    @Override
    public ProductCategoryResponse getProductCategoryById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para buscar");
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Relación producto-categoría no encontrada con el id: %d", id)));
        return ProductCategoryMapper.modelToProductCategoryResponse(productCategory);
    }

    @Override
    public ProductCategoryResponse createProductCategory(CreateProductCategoryRequest req) throws Exception {
        if (Objects.isNull(req)) throw new Exception("El objeto createProductCategoryRequest no puede ser nulo");
        if (req.getProductId() == null || req.getProductId() <= 0) throw new Exception("El campo productId debe contener un valor mayor a 0");
        if (req.getCategoryId() == null || req.getCategoryId() <= 0) throw new Exception("El campo categoryId debe contener un valor mayor a 0");
        Product product = productRepository.findById(req.getProductId()).orElseThrow(() -> new Exception("El producto no existe"));
        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(() -> new Exception("La categoría no existe"));
        ProductCategory productCategory = ProductCategoryMapper.createProductCategoryRequestToProductCategory(req, product, category);
        productCategory = productCategoryRepository.save(productCategory);
        return ProductCategoryMapper.modelToProductCategoryResponse(productCategory);
    }

    @Override
    public ProductCategoryResponse updateProductCategory(Integer id, UpdateProductCategoryRequest req) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para actualizar");
        if (Objects.isNull(req)) throw new Exception("El objeto updateProductCategoryRequest no puede ser nulo");
        if (req.getProductId() == null || req.getProductId() <= 0) throw new Exception("El campo productId debe contener un valor mayor a 0");
        if (req.getCategoryId() == null || req.getCategoryId() <= 0) throw new Exception("El campo categoryId debe contener un valor mayor a 0");

        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Relación producto-categoría no encontrada con el id: %d", id)));
        Product product = productRepository.findById(req.getProductId()).orElseThrow(() -> new Exception("El producto no existe"));
        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(() -> new Exception("La categoría no existe"));

        productCategory.setProduct(product);
        productCategory.setCategory(category);
        productCategory = productCategoryRepository.save(productCategory);
        return ProductCategoryMapper.modelToProductCategoryResponse(productCategory);
    }

    @Override
    public void deleteProductCategory(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido para eliminar");
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new Exception(String.format("ProductCategory no encontrado con el id: %d", id)));
        productCategoryRepository.delete(productCategory);
    }
}
