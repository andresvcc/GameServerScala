package Server

import akka.actor.{ActorSystem, Props}

object LoginServer extends App {
  val system = ActorSystem()
  val tcpserver = system.actorOf(Props(classOf[ActorTCPcManager], "localhost", 8080))
}


