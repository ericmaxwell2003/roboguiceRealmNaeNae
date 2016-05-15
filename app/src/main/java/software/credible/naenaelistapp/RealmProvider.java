package software.credible.naenaelistapp;

import javax.inject.Provider;

import io.realm.Realm;


public class RealmProvider implements Provider<Realm> {

    @Override
    public Realm get() {
        return Realm.getDefaultInstance();
    }
}
