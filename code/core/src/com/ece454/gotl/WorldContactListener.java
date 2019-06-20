package com.ece454.gotl;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact cntct) {
        Fixture fa = cntct.getFixtureA();
        Fixture fb = cntct.getFixtureB();
        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;
        if (isGroundContact(fa, fb)) {
            System.out.println("goose contact ground");
            Goose goose = (Goose) fb.getUserData();
            goose.setJumping(false);
        }
        if (isDangerContact(fa, fb)) {
            System.out.println("goose contact danger");
            Goose goose = (Goose) fb.getUserData();
            goose.setJumping(false);
            goose.hit();
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
            goose.setJumping(true);
        }
        if (isDangerContact(fa, fb)) {
            Goose goose = (Goose) fb.getUserData();
            goose.setJumping(true);
        }
    }

    private boolean isDangerContact(Fixture a, Fixture b) {
        return (a.getUserData() instanceof DangerZone && b.getUserData() instanceof Goose);
    }

    private boolean isGroundContact(Fixture a, Fixture b) {
        return (a.getUserData() instanceof Ground && b.getUserData() instanceof Goose);
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
}