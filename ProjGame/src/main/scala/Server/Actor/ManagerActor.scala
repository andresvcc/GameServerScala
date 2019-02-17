package Server.Actor

import akka.actor.{ActorRef, ActorSystem, Props}

case object ManagerActor{
  // default Actor constructor
  val system = ActorSystem("serverCOM")
  val loginActor: ActorRef = system.actorOf(Props[ActorLogin], name = "loginActor")
  val infoPlayerActor: ActorRef = system.actorOf(Props[ActorInfoPlayer], name = "infoPlayerActor")
  val infoServerActor: ActorRef = system.actorOf(Props[ActorInfoPlayer], name = "infoServerActor")
  val infoServerStatusActor: ActorRef = system.actorOf(Props[ActorInfoPlayer], name = "infoServerStatusActor")
  val bdPlayerActor: ActorRef = system.actorOf(Props[ActorBDPlayer], name ="bdPlayerActor")
}
