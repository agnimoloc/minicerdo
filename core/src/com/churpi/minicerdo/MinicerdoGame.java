package com.churpi.minicerdo;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.churpi.minicerdo.actors.CarActor;
import com.churpi.minicerdo.behaviors.CameraBehavior;
import com.churpi.minicerdo.behaviors.accessors.CameraAccessor;
import com.churpi.minicerdo.behaviors.accessors.CarAccessor;
import com.churpi.minicerdo.constants.AssetsLoadTypes;
import com.churpi.minicerdo.constants.GameEngine;
import com.churpi.minicerdo.screens.MainScreen;
import com.churpi.minicerdo.screens.SplashScreen;
import com.churpi.minicerdo.workers.AssetManagerWorker;

import javax.smartcardio.CardChannel;

public class MinicerdoGame extends Game {

	World world;
	Box2DDebugRenderer renderer;
	SpriteBatch batch;
	CameraBehavior camera;
    OrthographicCamera cameraUI;
	Viewport viewport, viewportUI;
	TweenManager tweenManager;

	AssetManager assetManager;

	ShapeRenderer shapeRenderer;

	private float accumulator = 0;


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

	public CameraBehavior getCamera(){
		return camera;
	}

    public OrthographicCamera getUICamera(){return cameraUI;}

	public AssetManager getAssetManager(){return assetManager;}

	@Override
	public void create () {

		assetManager = new AssetManager();

		world = new World(new Vector2(0,0), true);
		tweenManager = new TweenManager();
		Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
		Tween.registerAccessor(CarActor.class, new CarAccessor());

		renderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		shapeRenderer = new ShapeRenderer();

		OrthographicCamera orthographicCamera = new OrthographicCamera(Utils.pixelsToMeters(Gdx.graphics.getWidth()), Utils.pixelsToMeters(Gdx.graphics.getHeight()));
		viewport = new FillViewport(100,70, orthographicCamera);

        cameraUI = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewportUI = new FillViewport(100,70, cameraUI);

        //float ratio = 1400/Gdx.graphics.getHeight() ;

		//OrthographicCamera orthographicCamera = new OrthographicCamera(Utils.pixelsToMeters(Gdx.graphics.getWidth()*ratio), Utils.pixelsToMeters(Gdx.graphics.getHeight()*ratio));
		camera = new CameraBehavior(orthographicCamera, tweenManager);

		//setScreen(new MainScreen(this));
		setScreen(new SplashScreen(this, AssetsLoadTypes.BASIC));

	}

	@Override
	public void resize (int width, int height) {
		viewport.update(width,height);
        viewportUI.update(width,height);

        if (screen != null) screen.resize(width, height);
	}

	@Override
	public void render () {

		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();

		tweenManager.update(deltaTime);

		renderer.render(world, camera.getProjection());

		camera.update(deltaTime);

		world.step(GameEngine.TIME_STEP, GameEngine.VELOCITY_ITERATIONS, GameEngine.POSITION_ITERATIONS);
		/*float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= GameEngine.TIME_STEP) {
			world.step(GameEngine.TIME_STEP, GameEngine.VELOCITY_ITERATIONS, GameEngine.POSITION_ITERATIONS);
			accumulator -= GameEngine.TIME_STEP;
		}*/
	}


	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
		batch.dispose();
		super.dispose();
	}
}
