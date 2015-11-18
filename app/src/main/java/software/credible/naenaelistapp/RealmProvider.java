package software.credible.naenaelistapp;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Provider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmProvider implements Provider<Realm> {

    @Inject
    Context context;

    @Override
    public Realm get() {
        Realm.setDefaultConfiguration(
                new RealmConfiguration.Builder(context)
                        .deleteRealmIfMigrationNeeded()
                        .build());
        return Realm.getDefaultInstance();
    }
}
