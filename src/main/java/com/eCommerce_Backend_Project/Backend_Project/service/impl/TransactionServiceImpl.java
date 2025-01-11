package com.eCommerce_Backend_Project.Backend_Project.service.impl;

import com.eCommerce_Backend_Project.Backend_Project.data.product.entity.ProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseListData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.status.TransactionStatus;
import com.eCommerce_Backend_Project.Backend_Project.data.user.domainObject.request.FirebaseUserData;
import com.eCommerce_Backend_Project.Backend_Project.data.user.entity.UserEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.cartItem.entity.CartItemEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.domainObject.TransactionResponseData;
import com.eCommerce_Backend_Project.Backend_Project.data.transaction.entity.TransactionEntity;
import com.eCommerce_Backend_Project.Backend_Project.data.transactionProduct.entity.TransactionProductEntity;
import com.eCommerce_Backend_Project.Backend_Project.exception.TransactionException;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionProductRepository;
import com.eCommerce_Backend_Project.Backend_Project.repository.TransactionRepository;
import com.eCommerce_Backend_Project.Backend_Project.service.*;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionRepository transactionRepository;
    private final TransactionProductService transactionProductService;
    private final TransactionProductRepository transactionProductRepository;
    private final ProductService productService;


    public TransactionServiceImpl(UserService userService,
                                  CartItemService cartItemService,
                                  TransactionRepository transactionRepository,
                                  TransactionProductService transactionProductService,
                                  TransactionProductRepository transactionProductRepository,
                                  ProductService productService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
        this.transactionProductRepository = transactionProductRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public TransactionResponseData createTransaction(FirebaseUserData firebaseUserData){
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<CartItemEntity> cartItemEntityList = cartItemService.findAllByUser(loginUser);

            if(cartItemEntityList.isEmpty()){
                throw new TransactionException("Cart Item is empty");
            }

            TransactionEntity transactionEntity = new TransactionEntity(loginUser,cartItemEntityList);

            transactionEntity.setUser(loginUser);

            transactionRepository.save(transactionEntity);

            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.addCartItemtoTransactionProduct(firebaseUserData,transactionEntity);

            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
            return transactionResponseData;
        }catch (Exception ex){
            logger.warn("create transaction failed" + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public TransactionResponseData getTransactionDetailById(FirebaseUserData firebaseUserData, Integer tid){

        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());

            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductEntityList);
            return transactionResponseData;

        }catch (Exception ex){
            logger.warn("Get Transaction Detail failed: " + ex.getMessage());
            throw ex;
        }

    }

    @Override
    public List<TransactionResponseListData> getAllTransactionRecord(FirebaseUserData firebaseUserData){
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<TransactionEntity> transactionEntityList = transactionRepository.findByUser(loginUser);

            List<TransactionResponseListData> transactionResponseListDataList = new ArrayList<>();

            for(TransactionEntity transactionEntity: transactionEntityList){
                TransactionResponseListData transactionResponseListData = new TransactionResponseListData(transactionEntity);
                transactionResponseListDataList.add(transactionResponseListData);
            }

            return transactionResponseListDataList;

        }catch (Exception ex){
            logger.warn("Get all transaction Details failed: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    @Transactional
    public String updateTransactionStatus(FirebaseUserData firebaseUserData,Integer tid) throws StripeException {

        String YOUR_DOMAIN = "http://localhost:5173/";

        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);

            if(transactionEntity.getStatus() != TransactionStatus.PREPARE){
                throw new TransactionException("Status error");
            }


            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());
            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList){
                ProductEntity productEntity = productService.findBypid(transactionProductEntity.getPid());
                if(!productService.isValidQuantity(transactionProductEntity.getPid(),transactionProductEntity.getQuantity())){
                    throw new TransactionException(String.format("Not enough stock: Pid: %d Stock: %d",transactionProductEntity.getPid(),productEntity.getStock()));
                }

              lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(transactionProductEntity.getQuantity().longValue()) // 使用實際數量
                            .setPrice(productEntity.getStripePriceID()) // 假設你有價格 ID
                            .build()
            );
            }

            // 創建結帳會話
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT) // 使用支付模式
                    .setSuccessUrl(YOUR_DOMAIN + "thankyou/" + transactionTid.get().getTid())
                    .setCancelUrl(YOUR_DOMAIN + "error")
                    .addAllLineItem(lineItems) // 添加所有行項
                    .build();

            Session session = Session.create(params);

            transactionEntity.setStripeSessionId(session.getId());
            transactionEntity.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transactionEntity);

            return session.getUrl();

        }catch (Exception ex){
            logger.warn("Update Transaction Status: " + ex.getMessage());
            throw ex;
        }
    }

//    @Override
//    public String createCheckoutSession(FirebaseUserData firebaseUserData, Integer tid) {
//        String YOUR_DOMAIN = "http://localhost:5173/";
//        try {
//        UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
//        TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);
//
//        Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
//        List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());
//
//
//        // 建立會話的行項集合
//             List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
//
//        for (TransactionProductEntity transactionProductEntity : transactionProductEntityList){
//            ProductEntity productEntity = productService.findBypid(transactionProductEntity.getPid());
//            if(!productService.isValidQuantity(transactionProductEntity.getPid(),transactionProductEntity.getQuantity())){
//                throw new TransactionException(String.format("Not enough stock: Pid: %d Stock: %d",transactionProductEntity.getPid(),productEntity.getStock()));
//            }
//            // 將產品行項添加到行項列表中
//            lineItems.add(
//                    SessionCreateParams.LineItem.builder()
//                            .setQuantity(1L) // 使用實際數量
//                            .setPrice("price_1Q13ltBRyDFU5GVJxpnGTUc7") // 假設你有價格 ID
//                            .build()
//            );
//        }
//
//            // 創建結帳會話
//            SessionCreateParams params = SessionCreateParams.builder()
//                    .setMode(SessionCreateParams.Mode.PAYMENT) // 使用支付模式
//                    .setSuccessUrl(YOUR_DOMAIN + "thankyou")
//                    .setCancelUrl(YOUR_DOMAIN + "error")
//                    .addAllLineItem(lineItems) // 添加所有行項
//                    .build();
//
//            Session session = Session.create(params);
//            return session.getUrl();
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating session: " + e.getMessage(), e);
//        }
//    }

    @Override
    public TransactionResponseData finishTransaction(FirebaseUserData firebaseUserData, Integer tid) throws StripeException {
        try{
            UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = findTransactionUser(loginUser,tid);

            if(transactionEntity.getStatus() != TransactionStatus.PROCESSING){
                throw new TransactionException("Status error");
            }


//            Session session = Session.retrieve(transactionEntity.getStripeSessionId());
//
//            if(!"complete".equals(session.getPaymentStatus())){
//                throw new TransactionException("payment not yet finish");
//            }

            Optional<TransactionEntity> transactionTid = transactionRepository.findByTid(tid);
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.findTransactionProductList(transactionTid.get());

            for(TransactionProductEntity transactionProductEntity : transactionProductEntityList){
                productService.deductStock(transactionProductEntity.getPid(),transactionProductEntity.getQuantity());
            }

            cartItemService.emptyUserCart(firebaseUserData.getFirebaseUid());

            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transactionEntity);

            TransactionResponseData transactionResponseData = new TransactionResponseData(transactionEntity,transactionProductService.findTransactionProductList(transactionEntity));
            return transactionResponseData;

        }catch (Exception ex){
            logger.warn("Finish Transaction: " + ex.getMessage());
            throw ex;
        }
    }

    public TransactionEntity findTransactionUser(UserEntity loginUser, Integer tid) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findByUserAndTid(loginUser, tid);

        if (transactionEntity.isEmpty()) {
            throw new TransactionException(String.format("No this transaction,uid-%s,tid-%d",loginUser.getUid(),tid));
        }
        return transactionEntity.get();
    }

}
