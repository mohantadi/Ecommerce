package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    public static VBox orderpage=new VBox();
    Customer loggedInCustomer;
    HBox createHeaderBar;
    HBox createFooterBar;
    Button signInButton;
    Label welcomeLabel;
    VBox body;
    ProductList productList=new ProductList();
    VBox productPage;
    Button placeOrderButton=new Button("place order");

    ObservableList<Product>itemsInCart= FXCollections.observableArrayList();
    public BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
        //root.getChildren().add(loginPage);
        root.setTop(createHeaderBar);
        // root.setCenter(loginPage);
        body =new VBox();
        body.setPadding(new Insets(10));
        productPage=productList.getALlProducts();
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        body.getChildren().add(productPage);

        root.setBottom(createFooterBar);
        return root;
    }
    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createLoginPage() {
        Text userNameText = new Text("User Name");
        Text passwoardText = new Text("Passsword");
        TextField userName = new TextField();
        userName.setPromptText("ENTER USERNAME HERE");
        PasswordField password = new PasswordField();
        //password.setText();
        password.setPromptText("ENETER PASSWORD HERE");
        Label messageLabel = new Label("hi ");
        Button loginButton = new Button("LogIn");
        loginPage = new GridPane();
        //  loginPage.setStyle("-fx-background-color:grey");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwoardText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(messageLabel, 0, 2);
        loginPage.add(loginButton, 1, 2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        String name = userName.getText();
                                        String pass = password.getText();
                                        Login login = new Login();
                                        loggedInCustomer = login.customerLogin(name, pass);
                                        if (loggedInCustomer != null) {
                                            messageLabel.setText("Welcome " + loggedInCustomer.getName());
                                            welcomeLabel.setText("Welcome  " + loggedInCustomer.getName());
                                            createHeaderBar.getChildren().add(welcomeLabel);
                                            body.getChildren().clear();
                                            body.getChildren().add(productPage);
                                        } else {
                                            messageLabel.setText("Login failed Please use correct username and password");
                                        }
                                    }
                                }
        );
    }
    ObservableList<Product>itemsInOrder= FXCollections.observableArrayList();
    private void createHeaderBar(){
        Button homeButton=new Button("Home");
        Image image=new Image("C:\\Users\\MOHAN\\IdeaProjects\\ECommerce\\src\\ecommercedesigner.jpg");
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);
        TextField searchbar=new TextField();
        searchbar.setPromptText("search");
        searchbar.setPrefWidth(180);
        Button searchButton=new Button("search");

        signInButton=new Button("Sign In");
        welcomeLabel=new Label();
        Button cartButton=new Button("cart");
        Button orderButton=new Button("orders");
       // Button homeButton =new UserInterface("HOME");

        createHeaderBar=new HBox();
        createHeaderBar.setPadding(new Insets(20));
        createHeaderBar.setAlignment(Pos.CENTER);
        createHeaderBar.setSpacing(10);
        //createHeaderBar.setStyle("-fx-background-color:grey");
        createHeaderBar.getChildren().addAll(homeButton,searchbar,searchButton,signInButton,cartButton,orderButton);
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                createHeaderBar.getChildren().remove(signInButton);
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                VBox prodPage=productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                createFooterBar.setVisible(false);
            }
        });

        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                 orderpage=Order.getOrderProducts(itemsInOrder);
                orderpage.setAlignment(Pos.CENTER);
                orderpage.setSpacing(10);
                body.getChildren().add(orderpage);
                createFooterBar.setVisible(false);
            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //need list of product
                if(itemsInCart==null){
                    showDialog("Please add some products in cart to place order ");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("please login first to place order");
                }
                int count=Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                if(count!=0){
                    showDialog("order for "+count+" prouduct placed successfully");
                }
                else{
                    showDialog("order failed");
                }


            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                createFooterBar.setVisible(true);

                if(loggedInCustomer==null && createHeaderBar.getChildren().indexOf(signInButton)==-1){
                    createHeaderBar.getChildren().add(signInButton);
                }
            }


        });
    }
    private void createFooterBar(){
        Button buyNowButton=new Button("Buy now");
        Button addToCartButton=new Button("Add to cart");
        createFooterBar=new HBox();

        createFooterBar.setPadding(new Insets(20));
        createFooterBar.setAlignment(Pos.CENTER);
        createFooterBar.setSpacing(10);
        //createHeaderBar.setStyle("-fx-background-color:grey");
        createFooterBar.getChildren().addAll(buyNowButton,addToCartButton);
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product product = productList.getSelectedProduct();
                if (product == null) {
                    showDialog("Please select a product first to place order!");
                }
                if (loggedInCustomer == null) {
                    showDialog("please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
                if (status == true) {
                    itemsInOrder.add(product);
                    showDialog("order placed succesfuly");

                } else {
                    showDialog("order failed");
                }
            }  });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product product=productList.getSelectedProduct();
                if(product==null){
                    showDialog("Please select a product first to add it to cart");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to the cart successfully");

            }
        });
    }

    private  void showDialog(String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null) ;
        alert.setContentText(message);
        alert.setTitle("message");
        alert.showAndWait();
    }
}