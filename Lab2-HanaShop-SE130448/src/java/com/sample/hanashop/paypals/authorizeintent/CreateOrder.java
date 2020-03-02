package com.sample.hanashop.paypals.authorizeintent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.paypal.orders.*;

import com.paypal.http.HttpResponse;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import com.sample.hanashop.paypals.PayPalClient;

public class CreateOrder extends PayPalClient {

    /**
     * Method to create complete order body with <b>AUTHORIZE</b> intent
     *
     * @return OrderRequest with created order request
     */
    private OrderRequest buildCompleteRequestBody(String subTotal, String tax, String total, List<FoodsInBillDTO> listFoodInBill,
            String userReceiveName, String address, String street, String city) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("AUTHORIZE");
        ApplicationContext applicationContext = new ApplicationContext().brandName("Hana Shop")
                .landingPage("BILLING")
                .cancelUrl("http://localhost:8084/Lab2-HanaShop-SE130448/LoadShoppingCartController")
                .returnUrl("http://localhost:8084/Lab2-HanaShop-SE130448/PayByPaypalController")
                .userAction("CONTINUE")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId("CCCC")
                .description("Foods")
                .customId("CUST-Foods")
                .softDescriptor("Hana Shop - FOOD")
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode("USD")
                        .value(total)
                        .amountBreakdown(new AmountBreakdown()
                                .itemTotal(new Money().currencyCode("USD").value(subTotal))
                                .taxTotal(new Money().currencyCode("USD").value(tax))
                                .shipping(new Money().currencyCode("USD").value("00.00"))
                                .handling(new Money().currencyCode("USD").value("00.00"))
                                .shippingDiscount(new Money().currencyCode("USD").value("00.00"))
                        )
                )
                .items(new ArrayList<Item>() {
                    {
                        for (FoodsInBillDTO x : listFoodInBill) {
                            add(new Item()
                                    .name(x.getNameFood())
                                    .description(x.getShortDescription())
                                    .sku(x.getIdFood())
                                    .unitAmount(new Money().currencyCode("USD").value(x.getPrice() + ""))
                                    .tax(new Money().currencyCode("USD").value((x.getPrice() * 10 / 100) + ""))
                                    .quantity(x.getQuantity() + "")
                                    .category("PHYSICAL_GOODS")
                            );
                        }
                    }
                })
                .shippingDetail(new ShippingDetail().name(new Name().fullName(userReceiveName))
                        .addressPortable(new AddressPortable()
                                .addressLine1(address)
                                .addressLine2(street)
                                .adminArea2(city)
                                .adminArea1("CA")
                                .postalCode("94107")
                                .countryCode("US")
                        )
                );
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    /**
     * Method to create minimum required order body with <b>AUTHORIZE</b> intent
     *
     * @return OrderRequest with created order request
     */
    private OrderRequest buildMinimumRequestBody() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("AUTHORIZE");
        ApplicationContext applicationContext = new ApplicationContext()
                .cancelUrl("http://localhost:8084/Lab2-HanaShop-SE130448/LoadShoppingCartController")
                .returnUrl("http://localhost:8084/Lab2-HanaShop-SE130448/PayByPaypalController");
        orderRequest.applicationContext(applicationContext);
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("220.00"));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    public HttpResponse<Order> createOrder(boolean debug, String subTotal, String tax, String total, List<FoodsInBillDTO> listFoodInBill,
            String userReceiveName, String address, String street, String city) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer", "return=representation");
        request.requestBody(buildCompleteRequestBody(subTotal, tax, total, listFoodInBill, userReceiveName, address, street, city));
        HttpResponse<Order> response = client().execute(request);
//        if (debug) {
//            if (response.statusCode() == 201) {
//                System.out.println("Order with Complete Payload: ");
//                System.out.println("Status Code: " + response.statusCode());
//                System.out.println("Status: " + response.result().status());
//                System.out.println("Order ID: " + response.result().id());
//                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
//                System.out.println("Links: ");
//                for (LinkDescription link : response.result().links()) {
//                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
//                }
//                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
//                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
//                System.out.println("Full response body:");
//                System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
//            }
//        }
        return response;
    }

    public HttpResponse<Order> createOrderWithFullPayLoad(boolean debug, String subTotal, String tax, String total, List<FoodsInBillDTO> listFoodInBill,
            String userReceiveName, String address, String street, String city) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer", "return=representation");
        //request.header("content-type", "application/json");
        request.requestBody(buildCompleteRequestBody(subTotal, tax, total, listFoodInBill, userReceiveName, address, street, city));
        HttpResponse<Order> response = client().execute(request);
//        if (debug) {
//            if (response.statusCode() == 201) {
//                System.out.println("Order with Complete Payload: ");
//                System.out.println("Status Code: " + response.statusCode());
//                System.out.println("Status: " + response.result().status());
//                System.out.println("Order ID: " + response.result().id());
//                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
//                System.out.println("Links: ");
//                if (response.result().links() != null) {
//                    for (LinkDescription link : response.result().links()) {
//                        System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
//                    }
//                    System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
//                            + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
//                }
//
//                System.out.println("Full response body:");
//                System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
//            }
//        }
        return response;
    }

    /**
     * Method to create order with minimum required payload
     *
     * @param debug true = print response data
     * @return HttpResponse<Order> response received from API
     * @throws IOException Exceptions from API if any
     */
    public HttpResponse<Order> createOrderWithMinimumPayload(boolean debug) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer", "return=representation");
        request.requestBody(buildMinimumRequestBody());
        HttpResponse<Order> response = client().execute(request);
//        if (debug) {
//            if (response.statusCode() == 201) {
//                System.out.println("Order with Minimum Payload: ");
//                System.out.println("Status Code: " + response.statusCode());
//                System.out.println("Status: " + response.result().status());
//                System.out.println("Order ID: " + response.result().id());
//                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
//                System.out.println("Links: ");
//                for (LinkDescription link : response.result().links()) {
//                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
//                }
//                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
//                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
//                System.out.println("Full response body:");
//                System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
//            }
//        }
        return response;
    }

    /**
     * This is the driver function which invokes the createOrder function to
     * create an sample order.
     */
//	public static void main(String args[]) {
//		try {
//			new CreateOrder().createOrderWithFullPayLoad(true);
//			new CreateOrder().createOrderWithMinimumPayload(true);
//		} catch (com.paypal.http.exceptions.HttpException e) {
//			System.out.println(e.getLocalizedMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
