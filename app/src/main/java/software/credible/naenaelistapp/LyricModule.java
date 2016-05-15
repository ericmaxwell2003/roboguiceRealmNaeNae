package software.credible.naenaelistapp;

import com.google.inject.AbstractModule;

import io.realm.Realm;

public class LyricModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Realm.class).toProvider(RealmProvider.class);
    }
}
