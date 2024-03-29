package game.hangman.GUI;

import game.hangman.logic.GameSession;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class GameDiagram extends Pane {
    private static Pane diagram, arms, legs;
    private static Circle head;
    private static Line noose, body, leftArm, rightArm, leftLeg, rightLeg;

    // Constructor
    /**
     * Dude constructor, you know the dealio.
     */
    public GameDiagram() {
        diagram = new Pane();
        arms = new Pane();
        legs = new Pane();
        setMinWidth(400);
        getChildren().addAll(buildGallows());
        diagram.getChildren().addAll(arms, legs);
    }


    // Methods
    /**
     * Add body parts based on the mistake counter received from the Word.
     * Ignore body parts if they are already added to the Pane.
     */
    public static void addToDiagram() {
        switch(GameSession.mistakes) {
            case 1:
                if (!diagram.getChildren().contains(noose))
                    drawNoose();
                break;
            case 2:
                if (!diagram.getChildren().contains(head))
                    drawHead();
                break;
            case 3:
                if (!diagram.getChildren().contains(body))
                    drawBody();
                break;
            case 4:
                if (!arms.getChildren().contains(leftArm))
                    drawLeftArm();
                break;
            case 5:
                if (!arms.getChildren().contains(rightArm))
                    drawRightArm();
                break;
            case 6:
                if (!legs.getChildren().contains(leftLeg))
                    drawLeftLeg();
                break;
            case 7:
                if (!legs.getChildren().contains(rightLeg))
                    drawRightLeg();
                swingAnimation();
                System.out.println("HANGMAN: End Game, loss");
                // TODO score this game
                break;
            default:
                resetDiagram();
                System.out.println("HANGMAN: reset Dude diagram");
        }
    }

    /**
     * Make the diagram swing
     */
    private static void swingAnimation() {
        Arc nooseArc = new Arc(230,-15,100,100,260,20);
        PathTransition ptNoose = new PathTransition();
        Circle circle = new Circle(5);
        ptNoose.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        noose.endXProperty().bind(circle.translateXProperty().add(circle.centerXProperty()));
        noose.endYProperty().bind(circle.translateYProperty().add(circle.centerYProperty()));

        PathTransition ptHead = new PathTransition();
        Arc headArc = new Arc(230,15,100,100,255,30);
        ptHead.setPath(headArc);
        ptHead.setNode(head);

        Arc bodyTopArc = new Arc(230, 45, 100, 100, 250, 40);
        Circle circleTopBody = new Circle(5);
        PathTransition ptBodyTop = new PathTransition();
        body.startXProperty().bind(circleTopBody.translateXProperty().add(circleTopBody.centerXProperty()));
        body.startYProperty().bind(circleTopBody.translateYProperty().add(circleTopBody.centerYProperty()));
        ptBodyTop.setPath(bodyTopArc);
        ptBodyTop.setNode(circleTopBody);
        ptBodyTop.setCycleCount(Animation.INDEFINITE);
        ptBodyTop.setAutoReverse(true);
        ptBodyTop.setDuration(Duration.millis(5000));
        ptBodyTop.playFrom(Duration.millis(2500));

        Arc bodyBottomArc = new Arc(230, 155, 100, 100, 220, 100);
        Circle circleBottomBody = new Circle(5);
        PathTransition ptBodyBottom = new PathTransition();
        ptBodyBottom.setPath(bodyBottomArc);
        ptBodyBottom.setNode(circleBottomBody);
        ptBodyBottom.setCycleCount(Animation.INDEFINITE);
        ptBodyBottom.setAutoReverse(true);
        ptBodyBottom.setDuration(Duration.millis(5000));
        ptBodyBottom.playFrom(Duration.millis(2500));
        body.endXProperty().bind(circleBottomBody.translateXProperty().add(circleBottomBody.centerXProperty()));
        body.endYProperty().bind(circleBottomBody.translateYProperty().add(circleBottomBody.centerYProperty()));

        Arc armsArc = new Arc(150, -10, 100, 100, 241, 58);
        PathTransition ptArms = new PathTransition();
        armsArc.setFill(Color.RED);
        ptArms.setNode(arms);
        ptArms.setPath(armsArc);
        ptArms.setAutoReverse(true);
        ptArms.setCycleCount(Animation.INDEFINITE);
        ptArms.setDuration(Duration.millis(5000));
        ptArms.playFrom(Duration.millis(2500));

        Arc legsArc = new Arc(140, 77, 100, 100, 220, 100);
        legsArc.setFill(Color.GREEN);
        PathTransition ptLegs = new PathTransition();
        ptLegs.setNode(legs);
        ptLegs.setPath(legsArc);
        ptLegs.setAutoReverse(true);
        ptLegs.setCycleCount(Animation.INDEFINITE);
        ptLegs.setDuration(Duration.millis(5000));
        ptLegs.playFrom(Duration.millis(2500));

        ptHead.setAutoReverse(true);
        ptHead.setCycleCount(Animation.INDEFINITE);
        ptHead.setDuration(Duration.millis(5000));
        ptHead.playFrom(Duration.millis(2500));
        bodyTopArc.setStroke(Color.RED);

        ptNoose.setPath(nooseArc);
        ptNoose.setNode(circle);
        ptNoose.setAutoReverse(true);
        ptNoose.setCycleCount(Animation.INDEFINITE);
        ptNoose.setDuration(Duration.millis(5000));
        ptNoose.playFrom(Duration.millis(2500)); // start in the middle of the nooseArc
    }

    /**
     * Create the static background visuals
     * @return Pane pane
     */
    private Pane buildGallows() {
        Pane gallowsPane = new Pane();
        Arc standArc = new Arc(60, 400, 45, 30, 0, 180);
        standArc.setType(ArcType.OPEN);
        standArc.setStroke(Color.BLACK);
        standArc.setFill(null);

        Line standVertical = new Line(60, 30, 60, 370);
        Line standHorizontal = new Line(60, 30, 230, 30);

        gallowsPane.getChildren().addAll(standArc, standHorizontal, standVertical, diagram);

        return gallowsPane;
    }

    /**
     * Draw the different limbs and add them to the hanging diagram pane.
     */
    private static void drawNoose() {
        noose = new Line(230, 30, 230, 85);
        diagram.getChildren().addAll(noose); }
    private static void drawHead() {
        double radius = 30;
        head = new Circle(noose.getEndX(), noose.getEndY() + radius, radius);
        head.setFill(Color.TRANSPARENT);
        head.setStroke(Color.BLACK);
        diagram.getChildren().add(head); }
    private static void drawBody() {
        body = new Line(230, 145, 230, 255);
        diagram.getChildren().add(body); }
    private static void drawLeftArm() {
        leftArm = new Line(230, 180, 160, 150);
        arms.getChildren().add(leftArm); }
    private static void drawRightArm() {
        rightArm = new Line(230, 180, 300, 150);
        arms.getChildren().add(rightArm); }
    private static void drawLeftLeg() {
        leftLeg = new Line(230, 255, 180, 355);
        legs.getChildren().add(leftLeg); }
    private static void drawRightLeg() {
        rightLeg = new Line(230, 255, 280, 355);
        legs.getChildren().add(rightLeg); }
    private static void resetDiagram() {
        arms.getChildren().removeAll();
        legs.getChildren().removeAll();
        diagram.getChildren().removeAll(noose, arms, legs, body, head);
    }
}
