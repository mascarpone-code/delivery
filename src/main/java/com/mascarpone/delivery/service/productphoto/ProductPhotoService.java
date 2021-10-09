package com.mascarpone.delivery.service.productphoto;

import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.exception.InternalServerException;
import com.mascarpone.delivery.payload.productphoto.UploadProductPhotoRequest;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductPhotoService extends GeneralService<ProductPhoto> {
    Optional<ProductPhoto> findById(Long id);

    List<ProductPhoto> findAllByProductId(Long id);

    ResponseEntity<?> uploadProductPhoto(MultipartFile[] photos, Long productId, Long shopAdminId);

    ResponseEntity<?> getProductPhotos(Long productId);

    void getProductPhoto(Long id, HttpServletResponse response);

    ResponseEntity<?> deleteProductPhoto(Long photoId, Long shopAdminId);
}
