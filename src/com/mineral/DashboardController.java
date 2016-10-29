package com.mineral;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.mineral.StaticData.*;

public class DashboardController implements Initializable {

  @FXML private BorderPane menu;
  @FXML private BorderPane gameDashboard;
  @FXML private Pane menuBtn;
  @FXML private Pane author;
  @FXML private Pane gameCategoryInfoPane;
  @FXML private Slider playerCountSlider;
  @FXML private Text levelTxt;
  @FXML private Pane gamePartPane;
  @FXML private Pane activeCardPane;
  @FXML private Text categoryCardNameTxt;
  @FXML private Text categoryNameTxt;
  @FXML private Text categoryValueTxt;

  private Data data;
  private ArrayList<Text> userCardInfo;
  private boolean menuOpened = true;
  private boolean windowShowed = true;
  private Stage stage;
  private double x;
  private double y;
  private Pane cardInfo;
  private MenuButton menuButton;
  private MenuItem mi1, mi2, mi3, mi4, mi5; // menuItems for chacke new trump
  private MenuItem miTrump, miMagnetite; // Specific gravity or Magnetite
  private Button trumpBtn;
  private Text alertText;
  private Button passBtn;
  private Button accaptBtn;
  private Pane magnetite;
  private Text actionText;

  @FXML
  private void moveWindowEvent(MouseEvent me) {
    stage.setX(me.getScreenX() + x);
    stage.setY(me.getScreenY() + y);
  }

  @FXML
  private void selectWindowEvent(MouseEvent me) {
    stage = ((Stage) ((Node) (me.getSource())).getScene().getWindow());
    x = stage.getX() - me.getScreenX();
    y = stage.getY() - me.getScreenY();
  }

  @FXML
  private void toggleMenu(MouseEvent me) {

    if (menuOpened) {
      BorderPane.setMargin((Node) menu, new Insets(0, 0, 0, -280));
      menuBtn.setStyle(
          "-fx-background-image: url('"
              + IMAGES_URL
              + "ui/menuo.jpg');"
              + "-fx-background-size: cover");
      menuOpened = false;
    } else {
      BorderPane.setMargin((Node) menu, new Insets(0));
      menuBtn.setStyle(
          "-fx-background-image: url('"
              + IMAGES_URL
              + "ui/menuf.jpg');"
              + "-fx-background-size: cover");
      menuOpened = true;
    }
  }

  @FXML
  private void closeWindow(MouseEvent me) {
    ((Node) (me.getSource())).getScene().getWindow().hide();
  }

  @FXML
  private void resizeWindow(MouseEvent me) {

    Stage primaryStage = ((Stage) ((Node) (me.getSource())).getScene().getWindow());

    if (!windowShowed) {
      Screen screen = Screen.getPrimary();
      Rectangle2D bounds = screen.getVisualBounds();
      primaryStage.setX(bounds.getMinX());
      primaryStage.setY(bounds.getMinY());
      primaryStage.setWidth(bounds.getWidth());
      primaryStage.setHeight(bounds.getHeight());
      windowShowed = true;
    } else {
      primaryStage.setWidth(600);
      primaryStage.setHeight(400);
      windowShowed = false;
    }
  }

  @FXML
  private void toggleWindow(MouseEvent me) {
    ((Stage) ((Node) (me.getSource())).getScene().getWindow()).setIconified(true);
  }

  @FXML
  private void startGameEvent(ActionEvent ae) {

    System.out.println("Game started");

    data = new Data();

    data.playersCount = (int) playerCountSlider.getValue();

    System.out.println("players count - " + data.playersCount);

    data.playersCount = (int) playerCountSlider.getValue();

    showDashboard();

    BorderPane dealerBorder = getDealerBorder(data.playersCount);
    gameDashboard.setTop(dealerBorder);

    data.cards = new ArrayList<Card>(60);
    data.players = new ArrayList<Player>(data.playersCount);
    data.playerPanes = new ArrayList<Pane>(data.playersCount);
    Dealer.createCards(data.cards);

    data.rightVBox = getVBox();
    data.leftVBox = getVBox();

    if (data.playersCount == 3) {

      getPlayersWithCards(data.playersCount);

      data.playerPanes.add(getPlayerRobot(80, 80, 70, 10, 60, 70, 1, data.players.get(0)));
      data.playerPanes.add(getUserBorder(data.players.get(1)));
      data.playerPanes.add(getPlayerRobot(0, 80, 70, 80, 60, 70, 3, data.players.get(2)));

      data.rightVBox.getChildren().add(data.playerPanes.get(0));
      data.leftVBox.getChildren().add(data.playerPanes.get(2));
    }

    if (data.playersCount == 4) {

      getPlayersWithCards(data.playersCount);

      data.playerPanes.add(getPlayerRobot(80, 80, 70, 10, 60, 70, 1, data.players.get(0)));
      data.playerPanes.add(getPlayerRobot(80, 80, 70, 10, 60, 70, 2, data.players.get(1)));
      data.playerPanes.add(getUserBorder(data.players.get(2)));
      data.playerPanes.add(getPlayerRobot(0, 80, 70, 80, 60, 70, 4, data.players.get(3)));

      data.rightVBox.getChildren().addAll(data.playerPanes.get(0), data.playerPanes.get(1));
      data.leftVBox.getChildren().add(data.playerPanes.get(3));
    }

    if (data.playersCount == 5) {

      getPlayersWithCards(data.playersCount, 8);

      data.playerPanes.add(getPlayerRobot(80, 80, 70, 10, 60, 70, 1, data.players.get(0)));
      data.playerPanes.add(getPlayerRobot(80, 80, 70, 10, 60, 70, 2, data.players.get(1)));
      data.playerPanes.add(getUserBorder(data.players.get(2)));
      data.playerPanes.add(getPlayerRobot(0, 80, 70, 80, 60, 70, 4, data.players.get(3)));
      data.playerPanes.add(getPlayerRobot(0, 80, 70, 80, 60, 70, 5, data.players.get(4)));

      data.rightVBox.getChildren().addAll(data.playerPanes.get(0), data.playerPanes.get(1));
      data.leftVBox.getChildren().addAll(data.playerPanes.get(4), data.playerPanes.get(3));
    }

    showDashboardPanes(~~(data.playersCount / 2));
    data.category = new Category();
    activeCardPane.setOpacity(1);

    activeCardPane.setStyle("");

    data.players
        .get(0)
        .start(
            data.category,
            data,
            data.cards,
            data.players,
            activeCardPane,
            categoryCardNameTxt,
            categoryNameTxt,
            categoryValueTxt);
  }

  @FXML
  private void playAgainEvent(ActionEvent ae) {

    if (data == null) {
      playerCountSlider.setValue(3);
    } else {
      playerCountSlider.setValue(data.playersCount);
    }

    startGameEvent(ae);
  }

  private void accaptEvent(ActionEvent ae) {

    Player p = data.players.get(~~(data.playersCount / 2));

    if (p.canPlay == false) {
      return;
    }

    if (data.activeCard == null) {
      alertText(alertText, "Please choose card");
      return;
    }

    Card card = (Card) data.activeCard.getUserData();

    if (card.getType().equals(PLAYER_CARD)) {

      actionText.setText("");
      throwPlayCard(card);

      System.out.println("user throw card - " + card.getTitle());

    } else {
      actionText.setText("");
      throwTrumpCard(card);
      System.out.println("user throw trump card - " + card.getTitle());
    }
  }

  private void passEvent(ActionEvent ae) {

    Player p = data.players.get(~~(data.playersCount / 2));

    System.out.println("user press pass \n");
    if (Player.passedPlayer != null) {
      Player.passedPlayer.passed = false;
    }

    p.passed = true;
    p.canPlay = false;
    Player.passedPlayer = p;

    getNewCard(null);

    actionText.setText("");
    accaptBtn.setDisable(true);
    passBtn.setDisable(true);

    ++Dealer.gameOverFlag;
    //////////////////////////////////////////......................................
    nextPlayer();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    menuBtn.setStyle(
        "-fx-background-image: url('"
            + IMAGES_URL
            + "ui/menuf.jpg');"
            + "-fx-background-size: cover");

    Pane p = new Pane();
    p.setPrefWidth(160);
    p.setPrefHeight(78);
    p.setLayoutX(120);
    p.setLayoutY(-15);
    p.setStyle(
        "-fx-background-image: url('"
            + IMAGES_URL
            + "ui/JCUlogo.png');"
            + "-fx-background-size: cover");

    author.getChildren().add(p);

    System.out.println("Done!");
  }

  private BorderPane getDealerBorder(int players) {
    data.dealerBorder = new BorderPane();
    getDealerPane(players);
    data.dealerBorder.setCenter(data.dealerPane);

    return data.dealerBorder;
  }

  private Pane getDealerPane(int players) {
    data.dealerPane = new Pane();
    data.dealerPane.setMaxHeight(200);
    data.dealerPane.setMaxWidth(90);
    BorderPane.setAlignment(data.dealerPane, Pos.CENTER);

    getDealer();
    getAlertPane();
    getWinersPane();

    data.firstDealerCard = getDealerCard(10, 100, 92, 70, -15);
    data.middleDealerCard = getDealerCard(20, 110, 92, 70, 0);
    data.lastDealerCard = getDealerCard(27, 117, 92, 70, 0);

    data.dealerCardCountTxt = new Text();
    data.dealerCardCountTxt.setText("" + (60 - 8 * players));
    data.dealerCardCountTxt.setLayoutX(50);
    data.dealerCardCountTxt.setLayoutY(170);
    data.dealerCardCountTxt.setFont(new Font("System Bold", 20));

    data.dealerPane
        .getChildren()
        .addAll(
            data.dealer,
            data.firstDealerCard,
            data.middleDealerCard,
            data.lastDealerCard,
            data.dealerCardCountTxt,
            alertText,
            data.winersText);

    return data.dealerPane;
  }

  private Text getAlertPane() {

    alertText = new Text();
    alertText.setFill(Color.WHITE);
    alertText.setFont(new Font("System Bold", 12));
    alertText.setLayoutY(50);
    alertText.setLayoutX(90);

    return alertText;
  }

  private Text getWinersPane() {

    data.winersText = new Text();
    data.winersText.setFill(Color.WHITE);
    data.winersText.setFont(new Font("System Bold", 12));
    data.winersText.setLayoutY(70);
    data.winersText.setLayoutX(90);

    return data.winersText;
  }

  private Pane getDealer() {
    data.dealer = new Pane();
    data.dealer.setLayoutX(10);
    data.dealer.setLayoutY(14);
    data.dealer.setPrefHeight(80);
    data.dealer.setPrefWidth(70);
    data.dealer.setCursor(Cursor.HAND);

    String style = "-fx-background-color: #ffffff; -fx-background-radius: 10;";
    style += "-fx-background-image: url('" + IMAGES_URL + "ui/dealer.png');";
    style += "-fx-background-size: 100%; -fx-background-position: center bottom;";
    style += "-fx-background-repeat: no-repeat;";

    data.dealer.setStyle(style);

    data.dealerTxt = new Text();
    data.dealerTxt.setText("Dealer");
    data.dealerTxt.setLayoutX(15);
    data.dealerTxt.setLayoutY(17);

    data.dealer.getChildren().add(data.dealerTxt);

    return data.dealer;
  }

  private Pane getDealerCard(int x, int y, int h, int w, int r) {

    Pane p = new Pane();
    p.setLayoutX(x);
    p.setLayoutY(y);
    p.setPrefHeight(h);
    p.setPrefWidth(w);
    p.setRotate(r);

    String style = "-fx-background-radius: 10; ";
    style += "-fx-background-image: url('" + IMAGES_URL + "Slide66.jpg'); ";
    style += "-fx-background-size: cover; -fx-border-color: #ffffff; ";
    style += "-fx-border-width: 5; ";

    p.setStyle(style);

    return p;
  }

  private Pane getPlayerRobot(int x, int h, int w, int y1, int h1, int w1, int num, Player pl) {

    Pane p = new Pane();
    p.setPrefHeight(100);

    Pane r = getRobot(x, h, w, num);
    Pane c = getRobotCard(y1, h1, w1, pl);

    p.getChildren().addAll(c, r);

    return p;
  }

  private Pane getRobot(int x, int h, int w, int num) {

    Pane p = new Pane();
    p.setLayoutX(x);
    p.setPrefHeight(h);
    p.setPrefWidth(w);

    String s = "-fx-background-color: #ffffff; -fx-background-radius: 10;";
    s += "-fx-background-image: url('" + IMAGES_URL + "ui/player.png');";
    s += "-fx-background-size: 100%;-fx-background-position: center bottom;";
    s += "-fx-background-repeat: no-repeat;";

    p.setStyle(s);

    Text text = getPlayerText(num);
    p.getChildren().add(text);

    return p;
  }

  private Text getPlayerText(int num) {

    Text text = new Text();
    text.setText("Player " + num);
    text.setLayoutX(14);
    text.setLayoutY(17);

    return text;
  }

  private Pane getRobotCard(int y1, int h1, int w1, Player pl) {

    Pane p = new Pane();
    if (y1 == 10) {
      p.setLayoutY(y1);
    } else {
      p.setLayoutY(10);
      p.setLayoutX(y1);
    }
    p.setPrefHeight(h1);
    p.setPrefWidth(w1);

    String s = "-fx-background-radius: 5;";
    s += "-fx-background-image: url('" + IMAGES_URL + "Slide66.jpg');";
    s += "-fx-background-size: 100%;-fx-border-color: #ffffff;-fx-border-width: 5;";

    p.setStyle(s);

    Text text = getCardCountText(8);

    p.getChildren().add(text);

    pl.setCardTxt((Text) p.getChildren().get(0));

    return p;
  }

  private Text getCardCountText(int num) {
    Text t = new Text();
    t.setText(num + "");
    t.setLayoutX(29);
    t.setLayoutY(38);
    t.setFont(new Font("System Bold", 20));

    return t;
  }

  private VBox getVBox() {
    VBox box = new VBox();
    box.setPrefHeight(200);
    BorderPane.setAlignment(box, Pos.CENTER);
    return box;
  }

  private BorderPane getUserBorder(Player p) {

    BorderPane bp = new BorderPane();
    BorderPane.setAlignment(bp, Pos.CENTER);

    data.userCards = new HBox();
    data.userCards.setPrefHeight(200);
    BorderPane.setAlignment(data.userCards, Pos.CENTER);
    data.userCards.setPadding(new Insets(5, 5, 5, 5));

    ArrayList<Card> cards = p.getCards();

    String style =
        "-fx-background-size: cover;-fx-background-radius: 10;"
            + "-fx-background-repeat: no-repeat;-fx-border-color: #ffffff;"
            + "-fx-border-width: 3;-fx-border-radius: 10;"
            + "-fx-background-image: url('"
            + IMAGES_URL;

    for (int i = 0; i < 8; i++) {
      Card c = cards.get(i);
      String url = c.getUrl();
      String s = style + url + "');";
      Pane paneCard = getCardPane(i, s, c);

      data.userCards.getChildren().add(paneCard);
    }

    bp.setLeft(data.userCards);

    Pane userPane = new Pane();
    userPane.setPrefHeight(200);
    userPane.setPrefWidth(200);
    BorderPane.setAlignment(userPane, Pos.CENTER);

    Pane user = new Pane();
    user.setLayoutX(100);
    user.setLayoutY(60);
    user.setPrefHeight(80);
    user.setPrefWidth(70);

    String s = "-fx-background-color: #ffffff; -fx-background-radius: 10;";
    s += "-fx-background-image: url('" + IMAGES_URL + "ui/user.png');";
    s += "-fx-background-size: 100%;-fx-background-position: center bottom;";
    s += "-fx-background-repeat: no-repeat;";

    user.setStyle(s);

    Text text = getPlayerText(p.getId());
    user.getChildren().add(text);

    passBtn = new Button("PASS");
    passBtn.setTextFill(Color.WHITE);
    passBtn.setStyle("-fx-background-color: #af3232;");
    passBtn.setLayoutX(30);
    passBtn.setLayoutY(104);
    passBtn.setCursor(Cursor.HAND);
    passBtn.setOnAction(
        new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent ae) {
            passEvent(ae);
          }
        });

    p.setPassBtn(passBtn);

    accaptBtn = new Button("ACCAPT");
    accaptBtn.setTextFill(Color.WHITE);
    accaptBtn.setStyle("-fx-background-color: #32af32;");
    accaptBtn.setLayoutX(20);
    accaptBtn.setLayoutY(75);
    accaptBtn.setCursor(Cursor.HAND);
    accaptBtn.setOnAction(
        new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent ae) {
            accaptEvent(ae);
          }
        });

    p.setAccapt(accaptBtn);

    actionText = new Text("");
    actionText.setFill(Color.WHITE);
    actionText.setFont(new Font("System Bold", 18));
    actionText.setLayoutY(36);
    actionText.setLayoutX(25);

    p.setActionText(actionText);

    userPane.getChildren().addAll(user, passBtn, accaptBtn, actionText);

    cardInfo = new Pane();
    cardInfo.setPrefHeight(200);
    cardInfo.setPrefWidth(200);

    userCardInfo = new ArrayList<Text>(5);
    for (int j = 0, i = 15; j < CARD_INFO_COUNT; j++, i += 15) {
      userCardInfo.add(getUserCardInfo(i));
    }

    cardInfo
        .getChildren()
        .addAll(
            userCardInfo.get(0),
            userCardInfo.get(1),
            userCardInfo.get(2),
            userCardInfo.get(3),
            userCardInfo.get(4));

    bp.setCenter(cardInfo);

    bp.setRight(userPane);

    return bp;
  }

  private void mouseClick(MouseEvent me) {

    Pane first = (Pane) data.userCards.getChildren().get(0);

    if (data.activeCard != null && !(data.activeCard.equals(first))) {

      HBox.setMargin(data.activeCard, new Insets(0, 0, 0, -100));
    } else {
      if (data.activeCard != null) {
        HBox.setMargin(data.activeCard, new Insets(0, 0, 0, 0));
      }
    }

    Pane p = (Pane) me.getSource();
    if (p.equals(first)) {
      HBox.setMargin(p, new Insets(-40, 100, 40, 0));
    } else {
      HBox.setMargin(p, new Insets(-40, 100, 40, -100));
    }

    Card c = (Card) p.getUserData();

    String type = c.getType();

    if (menuButton != null) {
      cardInfo.getChildren().remove(menuButton);
    }

    if (trumpBtn != null) {
      cardInfo.getChildren().remove(trumpBtn);
    }

    for (int i = 0; i < CARD_INFO_COUNT; i++) {
      userCardInfo.get(i).setText("");
    }

    if (type.equals("play")) {
      userCardInfo.get(0).setText(HARDNESS + " - " + c.getHardness());
      userCardInfo.get(1).setText(SPECIFIC_GRAVITY + " - " + c.getSpecificGravity());
      userCardInfo.get(2).setText(CLEAVAGE + " - " + c.getCleavage());
      userCardInfo.get(3).setText(CRUSTAL_ABUNDANCE + " - " + c.getCrustalAbundance());
      userCardInfo.get(4).setText(ECONOMIC_VALUE + " - " + c.getEconomicValue());
    } else {

      String trump = c.getTitle();
      rightMenuButton(trump);
    }
    data.activeCard = p;
  }

  private void mouseMoved(MouseEvent me) {
    Pane p = (Pane) me.getSource();

    if (p.equals(data.activeCard)) {
      return;
    }

    Pane first = (Pane) data.userCards.getChildren().get(0);

    if (p.equals(first)) {
      HBox.setMargin(p, new Insets(-40, 100, 40, 0));
    } else {
      HBox.setMargin(p, new Insets(-40, 100, 40, -100));
    }
  }

  private void mouseExit(MouseEvent me) {
    Pane p = (Pane) me.getSource();

    if (p.equals(data.activeCard)) {
      return;
    }

    Pane first = (Pane) data.userCards.getChildren().get(0);

    if (p.equals(first)) {
      HBox.setMargin(p, new Insets(0));
    } else {
      HBox.setMargin(p, new Insets(0, 0, 0, -100));
    }
  }

  private void setCardEvents(Pane p) {

    p.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent me) {
            mouseMoved(me);
          }
        });

    p.setOnMouseExited(
        new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent me) {

            mouseExit(me);
          }
        });

    p.setOnMouseClicked(
        new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent me) {
            mouseClick(me);
          }
        });
  }

  private Pane getCardPane(int i, String style, Card card) {
    Pane paneCard = new Pane();
    paneCard.setStyle(style);
    paneCard.setPrefWidth(140);
    paneCard.setPrefHeight(190);
    paneCard.setCursor(Cursor.HAND);
    paneCard.setUserData(card);
    if (i > 0) {
      HBox.setMargin(paneCard, new Insets(0, 0, 0, -100));
    }

    setCardEvents(paneCard);

    return paneCard;
  }

  private void getNewCard(MouseEvent me) {
    Card c = Dealer.nextCard(data.cards);

    if (c == null) {
      return;
    }

    data.players.get(~~(data.playersCount / 2)).getCards().add(c);
    data.dealerCardCountTxt.setText(data.cards.size() + "");

    if (data.cards.size() == 2) {
      data.dealerPane.getChildren().remove(data.lastDealerCard);
    }
    if (data.cards.size() == 1) {
      data.dealerPane.getChildren().remove(data.middleDealerCard);
    }
    if (data.cards.size() == 0) {
      data.dealerPane.getChildren().remove(data.firstDealerCard);
    }

    String style = "-fx-background-size: cover;-fx-background-radius: 10;";
    style += "-fx-background-repeat: no-repeat;-fx-border-color: #ffffff;";
    style += "-fx-border-width: 3;-fx-border-radius: 10;";
    style += "-fx-background-image: url('" + IMAGES_URL;

    String s = style + c.getUrl() + "');";
    int size = data.userCards.getChildren().size();
    data.userCards.getChildren().add(getCardPane(size, s, c));

    data.pass = false;
  }

  private Text getUserCardInfo(int i) {

    Text name = new Text("");
    name.setFill(Color.WHITE);
    name.setFont(new Font("System Bold", 12));
    name.setLayoutY(i);

    return name;
  }

  private MenuButton getMenuButton(String name) {

    menuButton = new MenuButton(name);
    menuButton.setTextFill(Color.WHITE);
    menuButton.setFont(new Font("System Bold", 12));
    menuButton.setPrefWidth(180);
    menuButton.setLayoutX(10);
    menuButton.setLayoutY(85);
    menuButton.setStyle("-fx-background-color: #32af32");
    cardInfo.getChildren().add(menuButton);

    return menuButton;
  }

  private MenuItem getMenuItem(String name) {

    MenuItem mi = new MenuItem(name);

    if (name.equals(MAGNETITE)) {

      int size = data.userCards.getChildren().size();
      boolean flag = false;

      for (int i = 0; i < size; i++) {
        Pane p = (Pane) data.userCards.getChildren().get(i);
        Card card = (Card) p.getUserData();

        if (card.getTitle().equals(MAGNETITE)) {
          magnetite = p;
          flag = true;
          break;
        }
      }

      if (!flag) {
        mi.setDisable(true);
        return mi;
      }
    }

    mi.setOnAction(
            new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent ae) {

                MenuItem mi = (MenuItem) ae.getSource();
                chooseTrump(mi.getText());
              }
            });

    return mi;
  }

  private void setAllItems() {
    menuButton.getItems().setAll(mi1, mi2, mi3, mi4, mi5);
  }

  private void setTrumpOrMagnetite() {
    menuButton.getItems().addAll(miTrump, miMagnetite);
  }

  private void chooseTrump(String name) {
    menuButton.setText(name);
  }

  private void rightMenuButton(String trump) {
    if (trump.equals(THE_GEOLOGIST)) {
      getMenuButton(MENU_BUTTON_DEFAULT);

      mi1 = getMenuItem(HARDNESS);
      mi2 = getMenuItem(SPECIFIC_GRAVITY);
      mi3 = getMenuItem(CLEAVAGE);
      mi4 = getMenuItem(CRUSTAL_ABUNDANCE);
      mi5 = getMenuItem(ECONOMIC_VALUE);

      setAllItems();
      return;
    }

    if (trump.equals(THE_GEOPHYSICIST)) {
      getMenuButton(MENU_BUTTON_DEFAULT1);

      miTrump = getMenuItem(SPECIFIC_GRAVITY);
      miMagnetite = getMenuItem(MAGNETITE);

      setTrumpOrMagnetite();
      return;
    }

    if (trump.equals(THE_GEMMOLOGIST)) {
      getTrumpButton(HARDNESS);
      return;
    }
    if (trump.equals(THE_MINERALOGIST)) {
      getTrumpButton(CLEAVAGE);
      return;
    }
    if (trump.equals(THE_PETROLOGIST)) {
      getTrumpButton(CRUSTAL_ABUNDANCE);
      return;
    }
    if (trump.equals(THE_MINER)) {
      getTrumpButton(ECONOMIC_VALUE);
      return;
    }
  }

  private void showDashboard() {

    gameDashboard.setOpacity(1);
    String style = "-fx-background-image: url(\"com/mineral/resources/images/ui/b.jpg\");";
    style += "-fx-background-size: cover; -fx-background-position: center center";
    gameDashboard.setStyle(style);
  }

  private void showDashboardPanes(int num) {

    gameDashboard.setRight(data.rightVBox);
    gameDashboard.setLeft(data.leftVBox);
    gameDashboard.setBottom(data.playerPanes.get(num));
  }

  private void getPlayersWithCards(int num) {

    for (int i = 0, j = 1; i < num; i++, j++) {
      data.players.add(new Player(j, num % j));
      Dealer.dealCards(data.players.get(i), data.cards);
    }
  }

  private void getPlayersWithCards(int optimize, int more) {

    for (int i = 0, j = 1; i < optimize; i++, j++) {
      data.players.add(new Player(j, (optimize * more) % j));
      Dealer.dealCards(data.players.get(i), data.cards);
    }
  }

  private void getTrumpButton(String name) {

    trumpBtn = new Button(name);
    trumpBtn.setTextFill(Color.WHITE);
    trumpBtn.setFont(new Font("System Bold", 12));
    trumpBtn.setStyle("-fx-background-color: green");
    trumpBtn.setPrefHeight(25);
    trumpBtn.setPrefWidth(180);
    trumpBtn.setLayoutX(10);
    trumpBtn.setLayoutY(85);

    cardInfo.getChildren().add(trumpBtn);
  }

  private void alertText(Text text, String string) {
    text.setText(string);
    Dealer.alert();

    System.out.println("dealer - " + string);
  }

  private void throwPlayCard(Card card) {

    String category = data.category.getCategoryName();
    DDouble categoryValue = data.category.getCategoryValue();

    DDouble cardValue = card.getCardValue(category);

    if (categoryValue.compare(cardValue) > 0) {

      data.category.setCategoryValue(cardValue);

      categoryCardNameTxt.setText(card.getTitle());
      categoryNameTxt.setText("Category -- " + category);

      String valueTxt = null;

      if (!category.equals(HARDNESS) && !category.equals(SPECIFIC_GRAVITY)) {

        if (category.equals(CLEAVAGE)) {
          valueTxt = card.getCleavage();
        }

        if (category.equals(CRUSTAL_ABUNDANCE)) {
          valueTxt = card.getCrustalAbundance();
        }

        if (category.equals(ECONOMIC_VALUE)) {
          valueTxt = card.getEconomicValue();
        }

      } else {
        valueTxt = cardValue.getMaxValue() + "";
      }

      categoryValueTxt.setText("Value -- " + valueTxt);

      updateGame(card, data.activeCard);

      data.activeCard = null;
      alertText.setText("");

      for (int i = 0; i < CARD_INFO_COUNT; i++) {
        userCardInfo.get(i).setText("");
      }

      accaptBtn.setDisable(true);
      passBtn.setDisable(true);

      nextPlayer();

    } else {
      alertText(alertText, "You can't throw this card");
      return;
    }
  }

  private void throwTrumpCard(Card card) {
    String trump = card.getTitle();

    if (trump.equals(THE_GEOLOGIST)) {

      String newTrump = menuButton.getText();

      if (newTrump.equals(MENU_BUTTON_DEFAULT)) {
        alertText(alertText, "Please choose trump");
        return;
      }

      updateCategory(newTrump, card, -1);
      cardInfo.getChildren().remove(menuButton);
      updateGame(card, data.activeCard);

      accaptBtn.setDisable(true);
      passBtn.setDisable(true);

      nextPlayer();
      activationPlayer();
      return;
    }

    if (trump.equals(THE_GEOPHYSICIST)) {
      String trumpOrMagnetite = menuButton.getText();

      if (trumpOrMagnetite.equals(MENU_BUTTON_DEFAULT1)) {
        alertText(alertText, "Please choose trump or Magnetite");
        return;
      }

      if (trumpOrMagnetite.equals(SPECIFIC_GRAVITY)) {

        updateCategory(trumpOrMagnetite, card, -1);
        cardInfo.getChildren().remove(menuButton);
        updateGame(card, data.activeCard);

      } else {

        updateGame(card, data.activeCard);
        cardInfo.getChildren().remove(menuButton);

        Card c = (Card) magnetite.getUserData();
        String categoryName = data.category.getCategoryName();

        DDouble cardValue = c.getCardValue(categoryName);
        data.category.setCategoryValue(cardValue);
        categoryCardNameTxt.setText(c.getTitle());
        categoryValueTxt.setText("Value -- " + cardValue.getMaxValue());

        updateGame(c, magnetite);

        data.activeCard = null;
        alertText.setText("");

        for (int i = 0; i < CARD_INFO_COUNT; i++) {
          userCardInfo.get(i).setText("");
        }
      }

      accaptBtn.setDisable(true);
      passBtn.setDisable(true);

      nextPlayer();
      activationPlayer();
      return;
    }

    String trumpTxt = trumpBtn.getText();
    updateCategory(trumpTxt, card, -1);
    updateGame(card, data.activeCard);
    cardInfo.getChildren().remove(trumpBtn);

    accaptBtn.setDisable(true);
    passBtn.setDisable(true);

    nextPlayer();
    activationPlayer();
  }

  public static void nextRun(
      final Data data, final Player p, final Pane table, final Text... text) {
    Platform.runLater(
        new Runnable() {

          @Override
          public void run() {

            try {
              Thread.sleep(1500);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            if (p.getId() == data.players.size()) {
              data.players.get(0).run(data.category, data, data.cards, data.players, table, text);

            } else {

              data.players
                  .get(p.getId())
                  .run(data.category, data, data.cards, data.players, table, text);
            }
          }
        });
  }

  private void nextPlayer() {
    Platform.runLater(
            new Runnable() {

              @Override
              public void run() {

                try {
                  Thread.sleep(1500);
                } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }

                Player p = data.players.get(~~(data.playersCount / 2));

                if (p.getId() == data.players.size()) {
                  data.players
                          .get(0)
                          .run(
                                  data.category,
                      data,
                      data.cards,
                      data.players,
                      activeCardPane,
                      categoryCardNameTxt,
                      categoryNameTxt,
                      categoryValueTxt);
            } else {
              data.players
                  .get(p.getId())
                  .run(
                      data.category,
                      data,
                      data.cards,
                      data.players,
                      activeCardPane,
                      categoryCardNameTxt,
                      categoryNameTxt,
                      categoryValueTxt);
            }
          }
        });
  }

  private void activationPlayer() {

    if (Player.passedPlayer != null) {
      Player.passedPlayer.passed = false;
      Player.passedPlayer = null;
    }
  }

  private void updateGame(Card card, Pane activeCard) {

    Pane first = (Pane) data.userCards.getChildren().get(0);

    data.players.get(~~(data.playersCount / 2)).getCards().remove(card);
    data.players.get(~~(data.playersCount / 2)).getCards().trimToSize();

    Dealer.gameOverFlag = 0;

    String s = card.getUrl();

    activeCardPane.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");

    if (activeCard.equals(first) && data.userCards.getChildren().size() > 1) {
      Pane f = (Pane) data.userCards.getChildren().get(1);
      HBox.setMargin(f, new Insets(0));
    }

    data.userCards.getChildren().remove(activeCard);

    if (data.players.get(~~(data.playersCount / 2)).getCards().size() == 0) {
      String win = data.winersText.getText();

      if (win.length() == 0) {
        data.winersText.setText("WIN -> player" + (~~(data.playersCount / 2)));
      } else {
        data.winersText.setText(win + " -> player" + (~~(data.playersCount / 2)));
      }
    }
  }

  private void updateCategory(String newTrump, Card card, int value) {
    if (newTrump != null) {
      data.category.setCategoryName(newTrump);
      categoryNameTxt.setText("Category -- " + newTrump);
    }

    String cn = data.category.getCategoryName();
    String valueTxt = "";
    DDouble dValue = new DDouble(value);
    if (value != -1 && !cn.equals(HARDNESS) && !cn.equals(SPECIFIC_GRAVITY)) {

      valueTxt = data.category.getStringValue(cn, dValue);

    } else {
      if (value == -1) {
        valueTxt = "";
      } else {
        valueTxt = value + "";
      }
    }

    data.category.setCategoryValue(dValue);
    categoryCardNameTxt.setText(card.getTitle());

    categoryValueTxt.setText("Value -- " + valueTxt);
  }
}
