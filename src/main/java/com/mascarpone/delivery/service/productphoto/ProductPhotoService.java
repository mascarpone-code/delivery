package com.mascarpone.delivery.service.productphoto;

import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface ProductPhotoService extends GeneralService<ProductPhoto> {
    List<ProductPhoto> findAllByProductId(Long id);

    ResponseEntity<?> uploadProductPhoto(MultipartFile[] photos, Long productId, UUID shopAdminUuid);

    ResponseEntity<?> getProductPhotos(Long productId);

    void getProductPhoto(Long id, HttpServletResponse response);

    ResponseEntity<?> deleteProductPhoto(Long photoId, UUID shopAdminUuid);
}
