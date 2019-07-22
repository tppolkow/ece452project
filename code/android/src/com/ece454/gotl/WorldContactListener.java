package com.ece454.gotl;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import handlers.WorldManager;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact cntct) {
        Fixture fa = cntct.getFixtureA();
        Fixture fb = cntct.getFixtureB();
        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;
        Goose goose = (Goose) fb.getUserData();
        if (isGroundContact(fa, fb)) {
            System.out.println("goose contact ground");
        }
        if (isDangerContact(fa, fb)) {
            System.out.println("goose contact danger");
            goose.hit();
        }
        if (isCloudContact(fa, fb)) {
            System.out.println("Goose contact cloud, should end level");
            goose.setLevelEnd(true);
        }
        //level ends if goose hits bounds and is dead
        if (isBoundsContact(fa, fb) && goose.isDead()){
            System.out.println("dead goose contact bound, should reset level");
            goose.setLevelFailed(true);
        }
    }

    @Override
    public void endContact(Contact cntct) {
        Fixture fa = cntct.getFixtureA();
        Fixture fb = cntct.getFixtureB();
        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;
        if (isGroundContact(fa, fb)) {
            Goose goose = (Goose) fb.getUserData();
            goose.resetJumpCount();
        }
        if (isDangerContact(fa, fb)) {
            Goose goose = (Goose) fb.getUserData();
        }
    }

    private boolean isDangerContact(Fixture a, Fixture b) {
        return (a.getUserData() instanceof DangerZone && b.getUserData() instanceof Goose);
    }

    private boolean isGroundContact(Fixture a, Fixture b) {
        return (a.getUserData() instanceof Ground && b.getUserData() instanceof Goose);
    }

    private boolean isCloudContact(Fixture a, Fixture b) {
        return (a.getUserData() instanceof Cloud && b.getUserData() instanceof Goose);
    }

    private boolean isBoundsContact(Fixture a, Fixture b){
        return (a.getUserData() instanceof Bounds && b.getUserData() instanceof Goose);
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
        Fixture fa = cntct.getFixtureA();
        Fixture fb = cntct.getFixtureB();
        Goose goose = (Goose) fb.getUserData();
        //disable contact for death animation
        if (!isBoundsContact(fa, fb) && goose.isDead()) {
            cntct.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
}