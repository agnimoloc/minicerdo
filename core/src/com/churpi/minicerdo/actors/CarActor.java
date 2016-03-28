package com.churpi.minicerdo.actors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.Utils;
import com.churpi.minicerdo.behaviors.accessors.CarAccessor;
import com.churpi.minicerdo.messages.GameMessages;

/**
 * Created by agni_ on 06/09/2015.
 */
public class CarActor extends GenericActor implements Steerable<Vector2>, Telegraph {


    Sprite sprite;
    float boundingRadius;
    boolean tagged;
    FollowPath<Vector2, LinePath.LinePathParam> steeringBehavior;
    Array<LinePath<Vector2>> paths;

    Vector2 targetCamera;

    float[] maxLinearAcceleration = { 0, 5, 10, 20, 25 , 30 };// 20 - 100 (*20);
    float[] maxLinearSpeed = { 0, 2, 4, 6, 8, 10};// 5 - 25 (*5);
    int currentSpeed = 1;

    int currentPath = 0;

    boolean isPrincipal = false;

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());

    public CarActor(MinicerdoGame game, Vector2 position){
        super(game);
        createBox2D(game.getWorld(), position);

        sprite = new Sprite(new Texture("badlogic.jpg"));

        float width = 1;
        float height = 2;
        sprite.setSize(width * 2, height * 2);
        sprite.setOrigin(width, height);
        sprite.setPosition(position.x, position.y);

        this.boundingRadius = (width + height) / 4f;

    }

    public void setPaths(Array<Vector2>...roads){

        paths = new Array<LinePath<Vector2>>(roads.length);
        for(int i = 0; i < roads.length; i++){
            paths.add(new LinePath<Vector2>(roads[i]));
        }

        steeringBehavior = new FollowPath<Vector2, LinePath.LinePathParam>(this, paths.get(currentPath), 3f)
                .setDecelerationRadius(5)
                .setArrivalTolerance(1f)
                .setTimeToTarget(1f);

    }

    public void act(float delta) {

        if(steeringBehavior != null){
            if(isPrincipal) {
                steeringBehavior.setPredictionTime(.5f);
                steeringBehavior.calculateSteering(steeringOutput);
                targetCamera.set(steeringBehavior.getInternalTargetPosition());
            }

            steeringBehavior.setPredictionTime(0f);
            steeringBehavior.calculateSteering(steeringOutput);
            applySteering(steeringOutput, delta);

        }

        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.setPosition(body.getPosition().x - (sprite.getWidth() / 2), body.getPosition().y - (sprite.getHeight() / 2));

    }


    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {


        Vector2 linearVelocity = body.getLinearVelocity();

        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());
        body.setLinearVelocity(linearVelocity);

        if(linearVelocity.dst(Vector2.Zero) != 0) {
            float angle = (body.getLinearVelocity().angle() - 90) * MathUtils.degreesToRadians;
            body.setTransform(body.getPosition(), angle);
        }

    }

    public void draw(SpriteBatch batch) {
        ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.circle(steeringBehavior.getInternalTargetPosition().x, steeringBehavior.getInternalTargetPosition().y, Utils.pixelsToMeters(5f));
        shapeRenderer.setColor(0, 0, 1, 1);
        //shapeRenderer.circle(targetCamera.x, targetCamera.y, Utils.pixelsToMeters(5f));
        shapeRenderer.end();

        sprite.draw(batch);

    }

    public Vector2 getTargetCamera(){ return targetCamera; }

    private void createBox2D(World world, Vector2 position){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        shape.dispose();

    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return this.boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Vector2 newVector() {
        return body.getPosition().cpy();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Utils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Utils.angleToVector(outVector, angle);
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed[currentSpeed];
    } //26 m/s = 93.6 km/h

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration[currentSpeed];
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
    }

    @Override
    public float getMaxAngularSpeed() {
        return 510;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {

    }

    @Override
    public float getMaxAngularAcceleration() {
        return 5;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {

    }

    public boolean isPrincipal(){ return isPrincipal; }

    public void setPrincipal(boolean isPrincipal){
        if(isPrincipal && targetCamera == null){
            targetCamera = new Vector2(steeringBehavior.getInternalTargetPosition());
        }
        this.isPrincipal = isPrincipal;
    }


    @Override
    public boolean handleMessage(Telegram msg) {

        switch (msg.message) {
            case GameMessages.SPEED_DOWN:
                currentSpeed--;
                if(currentSpeed < 0){
                    currentSpeed = 0;
                }
                break;

            case GameMessages.SPEED_UP:
                currentSpeed++;
                if(currentSpeed == maxLinearSpeed.length){
                    currentSpeed = maxLinearSpeed.length -1;
                }
                break;
            case GameMessages.CHANGE_RIGHT:
                currentPath++;
                if (currentPath == paths.size) {
                    currentPath = paths.size - 1;
                }
                steeringBehavior.setPath(paths.get(currentPath));
                break;
            case GameMessages.CHANGE_LEFT:
                currentPath--;
                if (currentPath < 0) {
                    currentPath = 0;
                }
                steeringBehavior.setPath(paths.get(currentPath));
                break;
        }

        return false;
    }
}
