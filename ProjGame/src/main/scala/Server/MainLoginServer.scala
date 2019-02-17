/*
este es el main del servers con los proceso de login
*/

package Server

import akka.actor.{ActorSystem, Props}

object MainLoginServer extends App {
  //declaracion por defecto del systema de actores akka
  val system = ActorSystem()
  //instanciar un actor para la gestion de la comunicacion TCP/ip
  val tcpserver = system.actorOf(Props(classOf[ActorTCPcManager], "localhost", 8080))
}


