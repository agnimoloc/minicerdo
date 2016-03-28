package com.churpi.minicerdo.layers;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.Utils;
import com.churpi.minicerdo.actors.CarActor;
import com.churpi.minicerdo.messages.GameMessages;

/**
 * Created by agni_ on 06/09/2015.
 */
public class CityLayer extends GenericLayer {

    MessageManager messageManager;

    CarActor carActor;

    Array<Vector2> wayPoints;
    Array<Vector2> wayPoints2;



    public CityLayer(MinicerdoGame game){
        super(game);

        //wayPoints = createRandomPath(MathUtils.random(8, 15), 0, 0, 500,500);
        wayPoints = createRandomPath(MathUtils.random(8, 15), 0, 0, 150,150);
        wayPoints2 = new Array<Vector2>(wayPoints.size);
        BoundingBox box = new BoundingBox();

        for(int i = 0; i < wayPoints.size; i++){
            wayPoints.get(i).set(wayPoints.get(i).x -10, wayPoints.get(i).y -10 );
            wayPoints2.add(new Vector2(wayPoints.get(i).x +2, wayPoints.get(i).y +2 ));
            box.ext(wayPoints.get(i).x, wayPoints.get(i).y, 0);
        }

        carActor = new CarActor(game, wayPoints.first());
        carActor.setPaths(wayPoints, wayPoints2);
        carActor.setPrincipal(true);

        game.getCamera().setTargetPoint(carActor.getTargetCamera());
        //game.getCamera().setTargetPoint(new Vector2(box.getCenterX(),box.getCenterY()));
        game.getCamera().forceTargetPoint();

        initMessageManager();

    }

    private void initMessageManager(){

        messageManager = MessageManager.getInstance();

        messageManager.addListener(carActor, GameMessages.SPEED_UP);
        messageManager.addListener(carActor, GameMessages.SPEED_DOWN);
        messageManager.addListener(carActor, GameMessages.CHANGE_LEFT);
        messageManager.addListener(carActor, GameMessages.CHANGE_RIGHT);

    }

    public void act(float delta){
        carActor.act(delta);

        //game.getCamera().setTargetPoint(carActor.getTargetCamera());
        game.getCamera().setTargetPoint(carActor.getPosition());
    }

    public void draw(){
        game.getBatch().setProjectionMatrix(game.getCamera().getProjection());
        game.getBatch().begin();
        carActor.draw(game.getBatch());
        game.getBatch().end();

        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.setProjectionMatrix(game.getCamera().getProjection());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 1);
        for (int i = 0; i < wayPoints.size; i++) {
            int next = (i + 1) % wayPoints.size;
            if (next != 0) shapeRenderer.line(wayPoints.get(i), wayPoints.get(next));
            shapeRenderer.circle( wayPoints.get(i).x, wayPoints.get(i).y, Utils.pixelsToMeters(2f));
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        for (int i = 0; i < wayPoints2.size; i++) {
            int next = (i + 1) % wayPoints2.size;
            if (next != 0) shapeRenderer.line(wayPoints2.get(i), wayPoints2.get(next));
            shapeRenderer.circle( wayPoints2.get(i).x, wayPoints2.get(i).y, Utils.pixelsToMeters(2f));
        }
        shapeRenderer.end();



    }

    private Array<Vector2> createRandomPath (int numWaypoints, float minX, float minY, float maxX, float maxY) {
        Array<Vector2> wayPoints = new Array<Vector2>(numWaypoints);

        float midX = (maxX + minX) / 2f;
        float midY = (maxY + minY) / 2f;

        float smaller = Math.min(midX, midY);

        float spacing = MathUtils.PI2 / numWaypoints;

        for (int i = 0; i < numWaypoints; i++) {
            float radialDist = MathUtils.random(smaller * 0.2f, smaller);

            Vector2 temp = new Vector2(radialDist, 0.0f);

            rotateVectorAroundOrigin(temp, i * spacing);

            temp.x += midX;
            temp.y += midY;

            wayPoints.add(temp);
        }

        return wayPoints;
    }

    private static final Matrix3 tmpMatrix3 = new Matrix3();

    /** Rotates the specified vector angle rads around the origin */
    private static Vector2 rotateVectorAroundOrigin (Vector2 vector, float radians) {
        // Init and rotate the transformation matrix
        tmpMatrix3.idt().rotateRad(radians);

        // Now transform the object's vertices
        return vector.mul(tmpMatrix3);
    }

}
