

import Actors.UserActor;
import Actors.UserParentActor;
import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
@SuppressWarnings("unused")
public class Module extends AbstractModule implements AkkaGuiceSupport {

    @Override
    public void configure() {
        bindActor(UserParentActor.class, "userParentActor");
        bindActorFactory(UserActor.class, UserActor.Factory.class);
    }

}
