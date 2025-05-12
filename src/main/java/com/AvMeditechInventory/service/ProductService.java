/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface ProductService {

    public Result productCreate(ProductDto productDto, String sku, Map<String, Object> metadata, Integer gst, 
            String snoPattern, String[] hiddenInputContainer, String authToken, HttpServletRequest request);

    public DataResult<List<ProductDto>> productList(String authToken, Integer pageNumber, String after, String isAsc,
            String brand, String productType, String productName, HttpServletRequest request);

    public Result productUpdate(ProductDto productDto, String sku, String productVariantId, Integer gst, 
            String snoPattern, Map<String, Object> metadata, String[] hiddenInputContainer, String authToken, HttpServletRequest request);

    public DataResult<ProductDto> getProductById(String productId, String authToken, HttpServletRequest request);

    public Result deleteProductByProductId(String productId, String authToken, HttpServletRequest request);

    public List<ProductDto> readProductsFromCsv(MultipartFile multipartFile) throws Exception;

    public DataResult<List<String>> addProductFromCsv(List<ProductDto> productDtos, Integer pageNumber, String authToken,HttpServletRequest request);

    public DataResult<List<Response>> getServiceProductList(Date startDate, Date endDate, String productName, String customerName, String serialNumber, Integer pageNumber, Integer pageSize);

    public DataResult<List<Response>> getServiceDetailsBySaleId(Integer saleId);

    public Resource retrieveImage(Integer saleServiceId, HttpServletRequest request);

    public Result deleteProductsByProductIds(List<String> productIds, String authToken, HttpServletRequest request);

}
