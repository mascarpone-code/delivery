package com.mascarpone.delivery.controller.productphoto;

import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.productphoto.ProductPhotoService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductPhotoController {
    private final ProductPhotoService productPhotoService;

    /**
     * Shop uploads product photos.
     *
     * @param userPrincipal - authenticated shop admin
     * @param photos        - product photos
     * @param productId     - product id
     * @return list of product photos
     * @throws IOException
     */
    @PostMapping(path = "/api/shop/product/photo", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> uploadProductPhoto(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestParam("photos") MultipartFile[] photos,
                                                @RequestParam("productId") Long productId) throws IOException {
        UserAuthChecker.checkAuth(userPrincipal);
        return productPhotoService.uploadProductPhoto(photos, productId, userPrincipal.getId());
    }

    /**
     * Shop gets list of product photos
     *
     * @param productId - product id
     * @return list of product photos
     */
    @GetMapping(path = "/api/product/photo")
    public ResponseEntity<?> getProductPhotos(@RequestParam("productId") Long productId) {
        return productPhotoService.getProductPhotos(productId);
    }

    /**
     * Shop gets a product photo
     *
     * @param id       - product photo id
     * @param response - response
     */
    @GetMapping(path = "/api/product/photo/{id}")
    public void getProductPhoto(@PathVariable("id") Long id,
                                HttpServletResponse response) {
        productPhotoService.getProductPhoto(id, response);
    }

    /**
     * Shop deletes photo of product.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - product photo id
     * @return list of product photos
     */
    @DeleteMapping(path = "/api/shop/product/photo/{id}")
    public ResponseEntity<?> deleteProductPhoto(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return productPhotoService.deleteProductPhoto(id, userPrincipal.getId());
    }
}
