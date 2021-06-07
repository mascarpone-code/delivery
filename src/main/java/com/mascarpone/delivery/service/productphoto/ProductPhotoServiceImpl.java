package com.mascarpone.delivery.service.productphoto;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.exception.InternalServerException;
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

import static com.mascarpone.delivery.exception.ExceptionConstants.*;
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
     * @param photos      - product photos
     * @param productId   - product id
     * @param shopAdminId - shop admin id
     * @return list of product photos
     * @throws IOException
     */
    @Override
    public ResponseEntity<?> uploadProductPhoto(MultipartFile[] photos, Long productId, Long shopAdminId) throws IOException {
        User shopAdmin = userRepository.getOne(shopAdminId);
        Product product = productRepository.findByIdAndShop(productId, shopAdmin.getShop())
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));

        for (MultipartFile photo : photos) {
            ProductPhoto newProductPhoto = new ProductPhoto();
            newProductPhoto.setImage(resizeImage(photo));
            newProductPhoto.setMimeType(photo.getContentType());
            newProductPhoto.setProduct(product);
            productPhotoRepository.save(newProductPhoto);
            newProductPhoto.setPath(GET_PRODUCT_PHOTO_ENDPOINT + newProductPhoto.getId());
            productPhotoRepository.save(newProductPhoto);
        }

        List<ProductPhoto> productPhotos = productPhotoRepository.findAllByProduct(product);

        return ResponseEntity.ok(productPhotos);
    }

    /**
     * Shop gets list of product photos
     *
     * @param productId - product id
     * @return list of product photos
     */
    @Override
    public ResponseEntity<?> getProductPhotos(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(PRODUCT_NOT_FOUND));
        List<ProductPhoto> productPhotos = productPhotoRepository.findAllByProduct(product);

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
        ProductPhoto productPhoto = productPhotoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(PHOTO_NOT_FOUND));

        try {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(productPhoto.getImage());
            response.setContentType(productPhoto.getMimeType());
            IOUtils.copy(imageStream, response.getOutputStream());
            response.flushBuffer();
        } catch (NullPointerException | IOException ex) {
            throw new InternalServerException(ex.getMessage());
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
        User shopAdmin = userRepository.getOne(shopAdminId);
        ProductPhoto productPhoto = productPhotoRepository.findById(photoId)
                .orElseThrow(() -> new BadRequestException(PHOTO_NOT_FOUND));

        Shop productPhotoShop = productPhoto.getProduct().getShop();
        Shop adminShop = shopAdmin.getShop();

        if (productPhotoShop.equals(adminShop)) {
            productPhotoRepository.delete(productPhoto);
            Product product = productPhoto.getProduct();
            List<ProductPhoto> productPhotos = productPhotoRepository.findAllByProduct(product);

            return ResponseEntity.ok(productPhotos);
        } else {
            throw new BadRequestException(PHOTO_NOT_FOUND);
        }
    }
}

