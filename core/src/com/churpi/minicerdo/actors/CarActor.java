package com.churpi.minicerdo.actors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
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

/**
 * Created by agni_ on 06/09/2015.
 */
public class CarActor extends GenericActor implements Steerable<Vector2> {


    Sprite sprite;
    float boundingRadius;
    boolean tagged;
    FollowPath<Vector2, LinePath.LinePathParam> steeringBehavior;
    Array<LinePath<Vector2>> paths;

    float maxLinearAcceleration = 20;//= 100;
    float maxLinearSpeed = 5;//25;

    Vector2 targetCamera;

    int currentPath = 0;

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());

    public CarActor(MinicerdoGame game, Vector2 position){
        super(game);
        createBox2D(game.getWorld(), position);

        sprite = new Sprite(new Texture("badlogic.jpg"));
        //this.setPosition(body.getPosition().x, body.getPosition().y);
        float width = 1;
        float height = 2;
        sprite.setSize(width * 2, height * 2);
        sprite.setOrigin(width, height);
        sprite.setPosition(position.x, position.y);

        targetCamera= new Vector2(position);

        this.boundingRadius = (width + height) / 4f;

        /*game.getCamera().setFocusPoint(this);
        game.getCamera().forceFocusPoint();*/
        game.getCamera().setTargetPoint(position);
        game.getCamera().forceTargetPoint();

        /*Tween.set(this, CarAccessor.ACCELERATION).target(0);
        Tween.to(this, CarAccessor.ACCELERATION, 1f).target(100).ease(TweenEquations.easeNone).start(game.getTweenManager());*/

    }

    public void setPaths(Array<Vector2>...roads){

        paths = new Array<LinePath<Vector2>>(roads.length);
        for(int i = 0; i < roads.length; i++){
            paths.add(new LinePath<Vector2>(roads[i]));
        }

        steeringBehavior = new FollowPath<Vector2, LinePath.LinePathParam>(this, paths.get(currentPath), 3f)
                .setDecelerationRadius(10)
                .setArrivalTolerance(1f)
                .setTimeToTarget(0.1f);

    }

    float elapsedTime;

    public void act(float delta) {

        elapsedTime += delta;
        if(elapsedTime > 10f){
            Gdx.app.log("test", "yeii");
            elapsedTime = 0;

            currentPath ++;
            if(currentPath > paths.size -1 ){
                currentPath = 0;
            }
            steeringBehavior.setPath(paths.get(currentPath));
            game.getTweenManager().killTarget(this);

            /*if(body.getLinearVelocity().isZero()){

                Tween.set(this, CarAccessor.SPEED).target(25);
                Tween.to(this, CarAccessor.ACCELERATION, 5f).target(100).ease(TweenEquations.easeNone).start(game.getTweenManager());
            }else {
                Tween.to(this, CarAccessor.SPEED, 5f).target(0).ease(TweenEquations.easeNone).start(game.getTweenManager());
                Tween.set(this, CarAccessor.ACCELERATION).target(0);
            }*/


        }



        if(steeringBehavior != null){
            steeringBehavior.calculateSteering(steeringOutput);

            applySteering(steeringOutput, delta);


        }

        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.setPosition(body.getPosition().x - (sprite.getWidth() / 2), body.getPosition().y - (sprite.getHeight() / 2));

        targetCamera.set(body.getPosition());

        if(steeringBehavior != null){

            targetCamera.set(steeringBehavior.getInternalTargetPosition().x, steeringBehavior.getInternalTargetPosition().y);
            targetCamera.mulAdd(steeringOutput.linear.scl(5f), delta);
        }
        game.getCamera().setTargetPoint(targetCamera);
    }


    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {

        Vector2 linearVelocity = body.getLinearVelocity();
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());
        body.setLinearVelocity(linearVelocity);

        float angle =  body.getLinearVelocity().angle() - 90;

        if(steering.linear.isZero()){
            angle = body.getAngle() * MathUtils.radiansToDegrees;
        }

        body.setTransform(body.getPosition(), angle * MathUtils.degreesToRadians);

    }

    public void draw(SpriteBatch batch) {


        /*ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.circle(steeringBehavior.getInternalTargetPosition().x, steeringBehavior.getInternalTargetPosition().y, Utils.pixelsToMeters(5f));
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.circle(targetCamera.x, targetCamera.y, Utils.pixelsToMeters(5f));
        shapeRenderer.end();*/

        sprite.draw(batch);

    }

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
        return maxLinearSpeed;
    } //26 m/s = 93.6 km/h

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
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


}
