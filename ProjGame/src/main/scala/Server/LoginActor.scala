package Server

import akka.actor.Actor

class LoginActor extends Actor {
  import Command._
  import Comunicated._
  def receive:Receive = {
    case Connecting(name,senderName) =>
      if(connectingUser(name, senderName)){
        println("User Connect:" + name +": "+ senderName)
        sendMessage(senderName,"you are is now connected\n",serverMessage = true)
        sendToAll("", name+" is connected", serverMessage = true)
      }
    case Disconnect(senderName)=>
      val name = BdClient.findNameIdentities(senderName)
      if(disconnectUser(senderName)){
        println("User Disconnect: "+name+": "+ senderName)
        sendToAll("", name+" is disconnect§", serverMessage = true)
      }
    case _ => println("huh type command?")
  }
}
