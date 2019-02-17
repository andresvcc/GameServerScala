package Server.Actor

import akka.actor.{ActorRef, ActorSystem, Props}

case object ManagerActor{
  // default Actor constructor
  val system = ActorSystem("serverCOM")
  val loginActor1: ActorRef = system.actorOf(Props[ActorLogin], name = "loginActor1")
  val infoPlayerActor: ActorRef = system.actorOf(Props[ActorInfoPlayer], name = "selecPlayerActor")
}
