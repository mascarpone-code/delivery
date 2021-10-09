package com.mascarpone.delivery.service.productphoto;

import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.repository.product.ProductRepository;
import com.mascarpone.delivery.repository.productphoto.ProductPhotoRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.mascarpone.delivery.exception.ExceptionConstants.PHOTO_NOT_FOUND;
import static com.mascarpone.delivery.exception.ExceptionConstants.PRODUCT_NOT_FOUND;
import static com.mascarpone.delivery.utils.Constants.GET_PRODUCT_PHOTO_ENDPOINT;
import static com.mascarpone.delivery.utils.ImageResizer.resizeImage;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductPhotoServiceImpl implements ProductPhotoService {
    private final ProductPhotoRepository productPhotoRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductPhoto> getAll() {
        return productPhotoRepository.findAll();
    }

    @Override
    public void save(ProductPhoto object) {
        productPhotoRepository.save(object);
    }

    @Override
    public Optional<ProductPhoto> findById(Long id) {
        return productPhotoRepository.findById(id);
    }

    @Override
    public List<ProductPhoto> findAllByProductId(Long id) {
        return productPhotoRepository.findAllByProductId(id);
    }

    /**
     * Shop uploads product photos.
     *
     * @param multipartFiles - product photos
     * @param productId      - product id
     * @param shopAdminId    - shop admin id
     * @return list of product photos
     */
    @Override
    public ResponseEntity<?> uploadProductPhoto(MultipartFile[] multipartFiles, Long productId, Long shopAdminId) {
        var shopAdmin = userRepository.getOne(shopAdminId);
        var product = productRepository.findByIdAndShop(productId, shopAdmin.getShop())
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));

        for (var multipartFile : multipartFiles) {
            var newProductPhoto = new ProductPhoto();

            try {
                var multipartFileBytes = resizeImage(multipartFile);
                newProductPhoto.setImage(multipartFileBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            newProductPhoto.setMimeType(multipartFile.getContentType());
            newProductPhoto.setProduct(product);
            productPhotoRepository.save(newProductPhoto);
            newProductPhoto.setPath(GET_PRODUCT_PHOTO_ENDPOINT + newProductPhoto.getId());
            productPhotoRepository.save(newProductPhoto);
        }

        var productPhotoList = productPhotoRepository.findAllByProduct(product);

        return ResponseEntity.ok(productPhotoList);
    }

    /**
     * Shop gets list of product photos
     *
     * @param productId - product id
     * @return list of product photos
     */
    @Override
    public ResponseEntity<?> getProductPhotos(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        var productPhotos = productPhotoRepository.findAllByProduct(product);

        return ResponseEntity.ok(productPhotos);
    }

    /**
     * Shop gets a product photo
     *
     * @param id       - product photo id
     * @param response - response
     */
    @Override
    public void getProductPhoto(Long id, HttpServletResponse response) {
        var productPhoto = productPhotoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(PHOTO_NOT_FOUND));

        try {
            var imageStream = new ByteArrayInputStream(productPhoto.getImage());
            response.setContentType(productPhoto.getMimeType());
            IOUtils.copy(imageStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    /**
     * Shop deletes photo of product.
     *
     * @param photoId     - product photo id
     * @param shopAdminId - shop admin id
     * @return list of product photos
     */
    @Override
    public ResponseEntity<?> deleteProductPhoto(Long photoId, Long shopAdminId) {
        var shopAdmin = userRepository.getOne(shopAdminId);
        var productPhoto = productPhotoRepository.findById(photoId)
                .orElseThrow(() -> new BadRequestException(PHOTO_NOT_FOUND));

        var productPhotoShop = productPhoto.getProduct().getShop();
        var adminShop = shopAdmin.getShop();

        if (!productPhotoShop.equals(adminShop)) {
            throw new BadRequestException(PHOTO_NOT_FOUND);
        }

        productPhotoRepository.delete(productPhoto);
        var product = productPhoto.getProduct();
        var productPhotoList = productPhotoRepository.findAllByProduct(product);

        return ResponseEntity.ok(productPhotoList);
    }
}

