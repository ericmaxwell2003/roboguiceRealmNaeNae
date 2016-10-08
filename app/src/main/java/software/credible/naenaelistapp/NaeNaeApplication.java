package software.credible.naenaelistapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NaeNaeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
