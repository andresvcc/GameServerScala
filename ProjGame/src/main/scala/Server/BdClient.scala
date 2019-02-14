package Server

import akka.actor.ActorRef

case object BdClient {
  private val ActiveClients = scala.collection.mutable.HashMap.empty[String, ActorRef]
  private val ClientIdentities = scala.collection.mutable.HashMap.empty[String, String]
  private val ClientIdentities2 = scala.collection.mutable.HashMap.empty[String, String]


  def addActiveClient(name:String, senderName:ActorRef): Unit ={
    ActiveClients += (name -> senderName)
  }

  def supActiveClient(senderName:String): Unit ={
    ActiveClients -= senderName
  }

  def findActiveClient(senderName:String): ActorRef ={
    ActiveClients.getOrElse(senderName, null)
  }

  def allActorRef(): Iterable[ActorRef] ={
    ActiveClients.values
  }

  //------
  def nameExistsIdentities(name:String): Boolean ={
    if (ClientIdentities.getOrElse(name, null)==null) false
    else true
  }
  def senderExistsIdentities(senderName:String): Boolean ={
    if (ClientIdentities2.getOrElse(senderName, null)==null) false
    else true
  }

  def addClientIdentities(name:String, senderName:String): Unit ={
    ClientIdentities += (name -> senderName)
    ClientIdentities2 += (senderName -> name)
  }

  def supClientIdentities(name:String): Unit ={
    ClientIdentities2 -= findSenderIdentities(name)
    ClientIdentities -= name
  }
  def supClientIdentities2(senderName:String): Unit ={
    ClientIdentities -= findNameIdentities(senderName)
    ClientIdentities -= senderName
  }

  def findSenderIdentities(name:String): String ={
    ClientIdentities.getOrElse(name, null)
  }

  def findNameIdentities(senderName:String): String ={
    ClientIdentities2.getOrElse(senderName, null)
  }


}
