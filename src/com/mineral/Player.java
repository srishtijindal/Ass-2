package com.mineral;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import static com.mineral.StaticData.*;

public class Player {
  public static final int ROBOT_PLAYER = 0;
  public static final int USER_PLAYER = 1;
  public static int ii = 0;
  public static final int DEALER = 2;
  public static Player passedPlayer;
  public boolean passed = false;
  public boolean canPlay = false;
  public static final String[] categoryes = {
    HARDNESS, SPECIFIC_GRAVITY, CLEAVAGE, CRUSTAL_ABUNDANCE, ECONOMIC_VALUE
  };
  private ArrayList<Card> cards;
  private int id;
  private int type;
  private Text cardTxt;
  private Text action;

  private Button accaptBtn;
  private Button passBtn;

  public void setActionText(Text txt) {
    action = txt;
  }

  public void setAccapt(Button accaptBtn) {
    this.accaptBtn = accaptBtn;
  }

  public void setPassBtn(Button passBtn) {
    this.passBtn = passBtn;
  }

  public void setCardTxt(Text txt) {
    cardTxt = txt;
  }

  public Player(int id, int type) {
    cards = new ArrayList<>();
    this.id = id;
    this.type = type;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public int getId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public void start(
      Category cat,
      Data data,
      ArrayList<Card> dealerCards,
      ArrayList<Player> players,
      Pane table,
      Text... text) {

    int r = (int) Math.floor(Math.random() * categoryes.length);

    cat.setCategoryName(categoryes[r]);

    System.out.println("Start category - " + cat.getCategoryName());

    cat.setCategoryValue(new DDouble(-1));

    System.out.println("Start category value - -1");

    run(cat, data, dealerCards, players, table, text);
  }

  public void run(
      Category cat,
      Data data,

      Pane table,
      Text... text) {

    System.out.println("player " + this.getId() + " run");

    if (type != 1) {

      if (passed || cards.size() == 0) {
        DashboardController.nextRun(data, this, table, text); ///////////////<<<<<<<<<<<<,,,
        return;
      }

      String cn = cat.getCategoryName();
      DDouble cv = cat.getCategoryValue();

      Card goodCard = null;

      goodCard = setGoodPlayCard(cn, cv);

      if (goodCard == null) { ///////////////////////////////////none play card
        goodCard = getTrumpCard();

        if (goodCard == null) { ///////////////////////none trump card
          pass(cat, data, dealerCards, players, table, text);

          System.out.println("player" + this.getId() + " passed");

        } else { //////////////////////exist trump card

          String trumpName = goodCard.getTitle();
          throwTrumpCard(cat, data, goodCard, trumpName, table, text);

          System.out.println("player " + this.getId() + " throw trump card " + goodCard.getTitle());

          Dealer.gameOverFlag = 0;

          if (passedPlayer != null) {
            passedPlayer.passed = false;
            passedPlayer = null;
          }

          DashboardController.nextRun(data, this, table, text); ///////////////<<<<<<<<<<<<,,,
        }
      } else { ////////////////////////exist play card

        throwPlayCard(cat, data, goodCard, table, text);

        System.out.println("player - " + this.getId() + " throw card - " + goodCard.getTitle());
        Dealer.gameOverFlag = 0;

        DashboardController.nextRun(data, this, table, text); ///////////////<<<<<<<<<<<<,,,
      }

    } else {

      System.out.println("user round ");

      if (passed) {

        System.out.println("but user passed ");
        DashboardController.nextRun(data, this, table, text); ///////////////<<<<<<<<<<<<,,,

      } else {
        action.setText("Your round");

        canPlay = true;

        accaptBtn.setDisable(false);
        passBtn.setDisable(false);
      }
    }
  }

  private void pass(
      Category cat,
      Data data,
      ArrayList<Card> dealerCards,
      ArrayList<Player> players,
      Pane table,
      Text... text) {
    if (passedPlayer != null) {
      passedPlayer.passed = false;
    }
    passed = true;
    passedPlayer = this;

    if (dealerCards.size() > 0) {
      Card c = Dealer.nextCard(dealerCards);
      cards.add(c);
      cardTxt.setText(cards.size() + "");

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
    }

    if (isFin(data)) {
      return;
    }

    nextPlayer(data, table, text); /////////<<<<<<<<<<<<<<<<<<<
  }

  private Card setGoodPlayCard(String cn, DDouble cv) {

    DDouble helper = null;
    DDouble goodD = null;
    Card goodCard = null;

    int size = cards.size();

    for (int i = 0; i < size; i++) {
      Card card = cards.get(i);

      if (!card.getType().equals("play")) {
        continue;
      }

      helper = card.getCardValue(cn);

      if (cv.getMaxValue() < helper.getMaxValue()) {
        if (goodD != null && helper.getMaxValue() < goodD.getMaxValue()) {
          goodD = helper;
          goodCard = card;
          continue;
        }

        if (goodD != null && helper.getMaxValue() > goodD.getMaxValue()) {
          continue;
        }

        goodD = helper;
        goodCard = card;
      }
    }

    return goodCard;
  }

  private Card getTrumpCard() {
    int size = cards.size();
    for (int j = 0; j < size; j++) {
      Card card = cards.get(j);

      if (card.getType().equals("play")) {
        continue;
      }

      return card;
    }
    return null;
  }

  private void throwTrumpCard(
      Category cat, Data data, Card card, String trumpName, Pane table, Text... text) {

    if (trumpName.equals(THE_GEOLOGIST)) {
      int r = (int) Math.floor(Math.random() * categoryes.length);

      cat.setCategoryName(categoryes[r]);
      cat.setCategoryValue(new DDouble(-1));

      String s = card.getUrl();
      table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");

      text[0].setText(trumpName);
      text[1].setText(cat.getCategoryName());
      text[2].setText("Value -- ");

      cards.remove(card);
      cards.trimToSize();
      cardTxt.setText(cards.size() + "");

      if (cards.size() == 0) {
        seyWin(data);
      }

      return;
    }

    if (trumpName.equals(THE_GEOPHYSICIST)) {

      Card mag = null;
      int size = cards.size();
      for (int i = 0; i < size; i++) {

        if (cards.get(i).getTitle().equals(MAGNETITE)) {
          mag = cards.get(i);
          break;
        }
      }

      if (mag != null) {
        cards.remove(card);
        cards.trimToSize();
        cardTxt.setText(cards.size() + "");

        String catName = cat.getCategoryName();
        DDouble catValue = mag.getCardValue(catName);
        cat.setCategoryValue(catValue);

        String s = mag.getUrl();
        table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");
        text[0].setText(mag.getTitle());
        text[1].setText(catName);

        String strValue = "";

        if (!catName.equals(HARDNESS) && !catName.equals(SPECIFIC_GRAVITY)) {
          strValue = cat.getStringValue(catName, catValue);
        } else {
          strValue = catValue.getMaxValue() + "";
        }

        text[2].setText("Value -- " + strValue);

        cards.remove(mag);
        cards.trimToSize();
        cardTxt.setText(cards.size() + "");

        if (cards.size() == 0) {
          seyWin(data);
        }

        return;
      } else {
        cat.setCategoryName(categoryes[1]);
        cat.setCategoryValue(new DDouble(-1));

        String s = card.getUrl();
        table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");
        text[0].setText(trumpName);
        text[1].setText(cat.getCategoryName());
        text[2].setText("Value -- ");

        cards.remove(card);
        cards.trimToSize();
        cardTxt.setText(cards.size() + "");

        if (cards.size() == 0) {
          seyWin(data);
        }

        return;
      }
    }

    if (trumpName.equals(THE_GEMMOLOGIST)) {
      cat.setCategoryName(categoryes[0]);
      cat.setCategoryValue(new DDouble(-1));

      String s = card.getUrl();
      table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");
      text[0].setText(trumpName);
      text[1].setText(cat.getCategoryName());
      text[2].setText("Value -- ");

      cards.remove(card);
      cards.trimToSize();
      cardTxt.setText(cards.size() + "");

      if (cards.size() == 0) {
        seyWin(data);
      }

      return;
    }

    if (trumpName.equals(THE_MINERALOGIST)) {
      cat.setCategoryName(categoryes[2]);
      cat.setCategoryValue(new DDouble(-1));

      String s = card.getUrl();
      table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");
      text[0].setText(trumpName);
      text[1].setText(cat.getCategoryName());
      text[2].setText("Value -- ");

      cards.remove(card);
      cards.trimToSize();
      cardTxt.setText(cards.size() + "");

      if (cards.size() == 0) {
        seyWin(data);
      }

      return;
    }

    if (trumpName.equals(THE_PETROLOGIST)) {
      cat.setCategoryName(categoryes[3]);
      cat.setCategoryValue(new DDouble(-1));

      String s = card.getUrl();
      table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");
      text[0].setText(trumpName);
      text[1].setText(cat.getCategoryName());
      text[2].setText("Value -- ");

      cards.remove(card);
      cards.trimToSize();
      cardTxt.setText(cards.size() + "");

      if (cards.size() == 0) {
        seyWin(data);
      }

      return;
    }

    if (trumpName.equals(THE_MINER)) {
      cat.setCategoryName(categoryes[4]);
      cat.setCategoryValue(new DDouble(-1));

      String s = card.getUrl();
      table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");
      text[0].setText(trumpName);
      text[1].setText(cat.getCategoryName());
      text[2].setText("Value -- ");

      cards.remove(card);
      cards.trimToSize();
      cardTxt.setText(cards.size() + "");

      if (cards.size() == 0) {
        seyWin(data);
      }

      return;
    }
  }

  private void throwPlayCard(Category cat, Data data, Card card, Pane table, Text... text) {

    String catName = cat.getCategoryName();
    String strValue = "";
    DDouble dValue = card.getCardValue(catName);

    if (!catName.equals(HARDNESS) && !catName.equals(SPECIFIC_GRAVITY)) {
      strValue = cat.getStringValue(catName, dValue);
    } else {
      strValue = dValue.getMaxValue() + "";
    }

    cat.setCategoryValue(card.getCardValue(cat.getCategoryName()));

    String s = card.getUrl();
    table.setStyle("-fx-background-image: url('" + IMAGES_URL + s + "');");

    text[0].setText(card.getTitle());
    text[1].setText("Category -- " + cat.getCategoryName());
    text[2].setText("Value -- " + strValue);

    cards.remove(card);
    cards.trimToSize();
    cardTxt.setText(cards.size() + "");

    if (cards.size() == 0) {
      seyWin(data);
    }
  }

  private boolean isFin(Data data) {

    ++Dealer.gameOverFlag;

    if (Dealer.gameOverFlag >= (data.activePlayersCount * 2) && data.cards.size() == 0) {
      String win = data.winersText.getText();

      if (win.length() == 0) {

        int num = 100;
        int pId = 100;

        for (int i = 0; i < data.playersCount; i++) {
          if (num > data.players.get(i).getCards().size()) {
            num = data.players.get(i).getCards().size();
            pId = i + 1;
          }
        }

        data.winersText.setText("WIN -> player" + pId + " with " + num + "card(s)" + " and FIN");

      } else {
        data.winersText.setText(win + " -> AND FIN");
      }
      return true;
    }

    return false;
  }

  private void nextPlayer(final Data data, final Pane table, final Text... text) {
    Platform.runLater(
        new Runnable() {

          @Override
          public void run() {

            try {
              Thread.sleep(5000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            if (id == data.players.size()) {
              data.players.get(0).run(data.category, data, data.cards, data.players, table, text);
            } else {
              data.players.get(id).run(data.category, data, data.cards, data.players, table, text);
            }
          }
        });
  }

  private void seyWin(Data data) {
    String win = data.winersText.getText();
    --data.activePlayersCount;
    if (win.length() == 0) {
      data.winersText.setText("WIN -> player" + id);
    } else {
      data.winersText.setText(win + " -> player" + id);
    }
  }
}
