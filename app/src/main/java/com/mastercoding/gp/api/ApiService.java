package com.mastercoding.gp.api;

import com.mastercoding.gp.auth.login.data.LoginBody;
import com.mastercoding.gp.auth.login.data.LoginResponse;
import com.mastercoding.gp.auth.signup.data.SignUpBody;
import com.mastercoding.gp.auth.signup.data.SignUpResponse;
import com.mastercoding.gp.customer.data.AddPackageToOrderListBody;
import com.mastercoding.gp.customer.data.AddPackageToOrderListResponse;
import com.mastercoding.gp.customer.data.AddServiceToOrderListBody;
import com.mastercoding.gp.customer.data.AddServiceToOrderListResponse;
import com.mastercoding.gp.customer.data.ClearOrderResponse;
import com.mastercoding.gp.customer.data.ConfirmOrderResponse;
import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerCarDataBody;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.CustomerProfileBody;
import com.mastercoding.gp.customer.data.DeleteCarResponse;
import com.mastercoding.gp.customer.data.DeletePackageFromOrderListResponse;
import com.mastercoding.gp.customer.data.DeleteServiceFromOrderListResponse;
import com.mastercoding.gp.customer.data.GetProgressConfirmOrderResponse;
import com.mastercoding.gp.customer.data.OrderList;
import com.mastercoding.gp.customer.data.Package;
import com.mastercoding.gp.customer.data.Service;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // BaseURL =

    @POST("customers/register")
    Call<SignUpResponse> signup(@Body SignUpBody signUpBody);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @GET("customers/get-customer-by-id/{id}")
    Call<CustomerData> getCustomerById(@Path("id") int customerId, @Header("Authorization") String authHeader);

    @PUT("customers/edit-profile/{id}")
    Call<CustomerData> editCustomerProfile(@Path("id") int customerId,
                                          @Header("Authorization") String authHeader,
                                          @Body CustomerProfileBody customerProfileBody);

    @GET("customers/get-car-by-customer-id/{id}")
    Call<CustomerCarData> getCarByCustomerId(@Path("id") int customerId, @Header("Authorization") String authHeader);

    @PUT("customers/add-car")
    Call<CustomerCarData> addCar(@Body CustomerCarDataBody customerCarDataBody, @Header("Authorization") String authHeader);

    @PUT("customers/update-car/{id}")
    Call<CustomerCarData> updateCar(@Path("id") int carId, @Body CustomerCarDataBody customerCarDataBody, @Header("Authorization") String authHeader);

    @DELETE("customers/delete-car/{id}")
    Call<DeleteCarResponse> deleteCar(@Path("id") int carId, @Header("Authorization") String authHeader);

    // Services For Customers

    @GET("customers/get-all-cleaning-services")
    Call<List<Service>> getAllCleaningServices(@Header("Authorization") String authHeader);

    @GET("customers/get-all-maintenance-services")
    Call<List<Service>> getAllMaintenanceServices(@Header("Authorization") String authHeader);

    @GET("customers/get-all-take-away-services")
    Call<List<Service>> getAllTakeAwayServices(@Header("Authorization") String authHeader);

    //@HTTP(method = "get", path = "customers/get-service-by-id-and-branch-by-id", hasBody = true)
    @GET("customers/get-service-by-id-and-branch-by-id")
    Call<Service> getServiceByIdAndBranchId(@Query("serviceId") int serviceId, @Query("branchId") Long branchId, @Header("Authorization") String authHeader);

    @PUT("customers/add-service-to-order-list")
    Call<AddServiceToOrderListResponse> addServiceToOrderList(@Body AddServiceToOrderListBody addServiceToOrderListBody, @Header("Authorization") String authHeader);

    @DELETE("customers/delete-service-from-order-list/{id}")
    Call<DeleteServiceFromOrderListResponse> deleteServiceFromOrderList(@Path("id") int serviceId, @Header("Authorization") String authHeader);

    // Packages For Customers

    @GET("customers/get-all-packages")
    Call<List<Package>> getAllPackages(@Header("Authorization") String authHeader);

    @GET("customers/get-package-by-id-and-branch-by-id")
    Call<Package> getPackageByIdAndBranchId(@Query("packageId") int packageId, @Query("branchId") Long branchId, @Header("Authorization") String authHeader);

    @PUT("customers/add-package-to-order-list")
    Call<AddPackageToOrderListResponse> addPackageToOrderList(@Body AddPackageToOrderListBody addPackageToOrderListBody, @Header("Authorization") String authHeader);

    @DELETE("customers/delete-package-from-order-list/{id}")
    Call<DeletePackageFromOrderListResponse> deletePackageFromOrderList(@Path("id") int packageId, @Header("Authorization") String authHeader);



    @GET("customers/get-non-confirm-order")
    Call<OrderList> getNonConfirmOrder(@Query("customerId") int customerId, @Query("branchId") Long branchId, @Header("Authorization") String authHeader);

    @PUT("customers/confirm-order/{id}")
    Call<ConfirmOrderResponse> confirmOrder(@Path("id") int customerId, @Header("Authorization") String authHeader);

    @DELETE("customers/clear-order-list/{id}")
    Call<ClearOrderResponse> clearOrder(@Path("id") int customerId, @Header("Authorization") String authHeader);

    @GET("customers/get-progress-confirm-order/{id}")
    Call<GetProgressConfirmOrderResponse> getProgressConfirmOrder(@Path("id") int customerId, @Header("Authorization") String authHeader);



}
