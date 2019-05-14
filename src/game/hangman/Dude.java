package game.hangman;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Let's get some package private stuff started so the Hangman doesn't
 * have to deal with the ugliness of hosting all the lines and shit that
 * comes with the visual part of this game.
 */
class Dude extends Pane {
    // TODO Can I make fewer of these fuckers? Lawd
    private Pane dude;
    private Pane arms;
    private Pane legs;
    private Line noose;
    private Circle head;
    private Line body;
    private Line leftArm;
    private Line rightArm;
    private Line leftLeg;
    private Line rightLeg;

    /**
     * Constructor, you know the dealio.
     */
    Dude() {
        dude = new Pane();
        arms = new Pane();
        legs = new Pane();
        setMinWidth(400);

        dude.getChildren().addAll(arms, legs);
        getChildren().addAll(buildGallows());
    }


    /**
     * Refresh this object with a clear pane.
     * Making a Dude dude = new Dude(); doesn't seem to do the trick :(
     */
    void newDude() {
        getChildren().clear();
        dude = new Pane();
        arms = new Pane();
        legs = new Pane();
        setMinWidth(400);

        dude.getChildren().addAll(arms, legs);
        getChildren().addAll(buildGallows());
    }

    /**
     * Add body parts based on the mistake counter received from the Word.
     * Ignore body parts if they are already added to the Pane.
     * // TODO Maybe change parts to static so there can only be 1?
     * @param n Wrong guess counter
     */
    void addToDude(int n) {
        switch(n) {
            case 1:
                if (!dude.getChildren().contains(noose))
                    drawNoose();
                break;
            case 2:
                if (!dude.getChildren().contains(head))
                    drawHead();
                break;
            case 3:
                if (!dude.getChildren().contains(body))
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
                System.out.println("End Game");
                break;
            default:
                undrawDude();
                System.out.println("Undraw Dude");
        }
    }

    /**
     * Make the dude swing
     */
    private void swingAnimation() {
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

        gallowsPane.getChildren().addAll(standArc, standHorizontal, standVertical, dude);

        return gallowsPane;
    }

    /**
     * Draw the different limbs and add them to the hanging dude pane.
     */
    private void drawNoose() {
        noose = new Line(230, 30, 230, 85);

        dude.getChildren().addAll(noose);
    }
    private void drawHead() {
        double radius = 30;
        head = new Circle(noose.getEndX(), noose.getEndY() + radius, radius);
        head.setFill(Color.TRANSPARENT);
        head.setStroke(Color.BLACK);

        dude.getChildren().add(head); }
    private void drawBody() {
        body = new Line(230, 145, 230, 255);

        dude.getChildren().add(body); }
    private void drawLeftArm() {
        leftArm = new Line(230, 180, 160, 150);

        arms.getChildren().add(leftArm); }
    private void drawRightArm() {
        rightArm = new Line(230, 180, 300, 150);

        arms.getChildren().add(rightArm); }
    private void drawLeftLeg() {
        leftLeg = new Line(230, 255, 180, 355);

        legs.getChildren().add(leftLeg); }
    private void drawRightLeg() {
        rightLeg = new Line(230, 255, 280, 355);

        legs.getChildren().add(rightLeg); }
    private void undrawDude() {
        arms.getChildren().removeAll();
        legs.getChildren().removeAll();
        dude.getChildren().removeAll(noose, arms, legs, body, head);
    }
}
