package Server

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.io.Tcp.{Bind, Bound, Connected, Register}
import akka.io.{IO, Tcp}

class ActorTCPcManager(address: String, port: Int) extends Actor {
  import context.system
  IO(Tcp) ! Bind(self, new InetSocketAddress(address, port))

  override def receive: Receive = {
    case Bound(local) =>
      println(s"Server started on $local")
    case Connected(remote, local) =>
      val handler = context.actorOf(Props(classOf[ActorTCPcHandler], s"$remote"))
      println(s"New connection: $local -> $remote")
      BdClient.addActiveClient(sender.path.name, sender)
      println("path name: "+sender.path.name +" ->  ActorRef"+ sender)
      sender() ! Register(handler)
  }
}


