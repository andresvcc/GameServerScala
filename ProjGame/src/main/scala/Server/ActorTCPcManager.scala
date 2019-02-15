/*
este modelo de actor gestiona las conexiones TCP/ip con los posibles usuarios
antes de loguearse o hacer cualquier cosa se le asigna a este actor la tarea de
establecer una conexion y registrarlo como desconocido conectado.
*/

package Server

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.io.Tcp.{Bind, Bound, Connected, Register}
import akka.io.{IO, Tcp}

class ActorTCPcManager(address: String, port: Int) extends Actor {
  import context.system
  IO(Tcp) ! Bind(self, new InetSocketAddress(address, port)) // conexion TCP/ip del server

  override def receive: Receive = {
    case Bound(local) =>
      println(s"Server started on $local")
    // en el caso de recibir una conexion
    case Connected(remote, local) =>
      // handler es una instancia del modelo ActorTCPcHandler
      val handler = context.actorOf(Props(classOf[ActorTCPcHandler], s"$remote"))
      println(s"New connection: $local -> $remote")
      // se agrega el nuevo usuario a la lista de usuarios sin identificar
      // en este contexto sender es el ActorRef  y sender.path.name es el identificador de conexion de usuario
      BdClient.addActiveClient(sender.path.name, sender)
      println("path name: "+sender.path.name +" ->  ActorRef"+ sender)
      // se envia el mensaje a sender() del protocolo TCP/ip para delegar la gestion de esta conexion a el Actor handler
      sender() ! Register(handler)
  }
}


