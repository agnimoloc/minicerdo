package com.churpi.minicerdo;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.churpi.minicerdo.behaviors.CameraBehavior;
import com.churpi.minicerdo.behaviors.accessors.CameraAccessor;
import com.churpi.minicerdo.screens.MainScreen;

public class MinicerdoGame extends Game {

	World world;
	Box2DDebugRenderer renderer;
	SpriteBatch batch;
	CameraBehavior camera;
	TweenManager tweenManager;

	ShapeRenderer shapeRenderer;



	public World getWorld(){
		return world;
	}

	public TweenManager getTweenManager(){
		return tweenManager;
	}

	public  SpriteBatch getBatch(){
		return batch;
	}

	public ShapeRenderer getShapeRenderer(){ return  shapeRenderer;}

	public OrthographicCamera getCamera(){
		return camera.getOrthographicCamera();
	}


	@Override
	public void create () {
		world = new World(new Vector2(0,0), true);
		tweenManager = new TweenManager();

		renderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		shapeRenderer = new ShapeRenderer();

		OrthographicCamera orthographicCamera = new OrthographicCamera(Utils.pixelsToMeters(Gdx.graphics.getWidth()), Utils.pixelsToMeters(Gdx.graphics.getHeight()));
		camera = new CameraBehavior(orthographicCamera, tweenManager);

		setScreen(new MainScreen(this));

	}


	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(deltaTime, 8, 3);

		tweenManager.update(deltaTime);
		camera.update();

		renderer.render(world, camera.getProjection());

		super.render();
	}

	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
		batch.dispose();
		super.dispose();
	}
}