package Server.BD

import akka.actor.ActorRef

object BdClientActive{
  private val ActiveClients = scala.collection.mutable.HashMap.empty[String, ActorRef]

  def addActiveClient(senderName:String, actorRef: ActorRef): Unit ={
    ActiveClients += (senderName -> actorRef)
  }

  //suprimir un cliente del diccionario ActiveClients
  def supActiveClient(senderName:String): Unit ={
    ActiveClients -= senderName
  }

  //buscar un cliente al diccionario ActiveClients
  def findActiveClient(senderName:String): ActorRef ={
    ActiveClients.getOrElse(senderName, null)
  }

  //obtener el conjunto de ActorRef en ActiveClient
  def allActorRef(): Iterable[ActorRef] ={
    ActiveClients.values
  }

  //obtener el conjunto de senderName en ActiveClient
  def allSenderName(): Iterable[String] ={
    ActiveClients.keySet
  }

  def exist(senderName:String): Boolean ={
    ActiveClients.contains(senderName)
  }
}
