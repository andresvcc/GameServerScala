package Server


import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.Tcp.Write
import akka.util.ByteString

case object Command{
  //-----------------------------------------
  val CommandCharacter = "~"
  val system = ActorSystem("serverCOM")
  // default Actor constructor
  val loginActor1: ActorRef = system.actorOf(Props[LoginActor], name = "loginActor1")


  //-----------------------------------------
  def getCommand(command:String):Array[String] = {
    command.split(" ")
  }

  def isCommand(message: String): Boolean = {
    message.startsWith(CommandCharacter)
  }

  def connectingUser(name:String, senderName:String): Boolean ={

    if(BdClient.senderExistsIdentities(senderName)){
      sendMessage( senderName,"is not possible, your are connected!")
      false
    }else if (BdClient.nameExistsIdentities(name)) {
      sendMessage( senderName,"There is already an user with this username!")
      false
    } else {
      BdClient.addClientIdentities(name, senderName)
      true
    }
  }

  def disconnectUser(senderName:String): Boolean ={
    BdClient.findActiveClient(senderName)
    BdClient.supActiveClient(senderName)
    if (BdClient.senderExistsIdentities(senderName)) {
      BdClient.supClientIdentities2(senderName)
      BdClient.supActiveClient(senderName)
      true
    } else {
      println("not user in activeClient!")
      false
    }
  }

  def getActorRefByName(name: String): ActorRef = {
    BdClient.findActiveClient(name)
  }

  def sendMessage(clientActorName: String, message: String, serverMessage: Boolean = false): Unit = {
    val actorRef = getActorRefByName(clientActorName)
    if(actorRef != null){
      if (serverMessage) {
        actorRef ! Write(ByteString("[SERVER]: " + message))
      } else {
        actorRef ! Write(ByteString(message))
      }
    }else{
      println("user no found")
    }
  }

  def sendToAll(messageSender: String, message: String, serverMessage: Boolean = false): Unit = {
    if (serverMessage) {
      BdClient.allActorRef().foreach(actorRef => actorRef ! Write(ByteString("[SERVER for ALL]: " + message)))
    } else {
      BdClient.allActorRef().foreach(actorRef => actorRef ! Write(ByteString("<" + messageSender + ">: " + message)))
    }
  }
}

