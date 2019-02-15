package Server

import akka.actor.Actor
import akka.io.Tcp.{ConnectionClosed, Received}

class ActorTCPcHandler(arg:String) extends Actor {
  import Command._
  import Comunicated._
  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      val cmd:Array[String] = getCommand(decoded)
      cmd(0) match {//cmd(0)-> command
        case "~login" =>
            if (cmd.length > 1 && cmd(1).length > 3) loginActor1 ! Connecting(cmd(1), sender.path.name)
            else sendMessage(sender.path.name,"the username is not correct")
        case _ =>
            sendMessage(sender.path.name,"Command "+cmd(0)+" not found")
        }
    case message: ConnectionClosed =>
      print("Connection has been closed "+arg+" -> ")
      loginActor1 ! Disconnect(sender.path.name)
      context.stop(self)
  }
}
