package Server

import akka.actor.ActorRef

object BdPlayerTempConnect{

  //diccionario de clientes activos senderName -> PlayerTempConnect
  private val playerSender = scala.collection.mutable.HashMap.empty[String, PlayerTempConnect]
  private val playerName = scala.collection.mutable.HashMap.empty[String, PlayerTempConnect]

  //agregar un cliente al diccionario Player
  def addPlayer(name:String, senderName:String, host:String): Boolean ={
    val actorRef:ActorRef = ClientActive.findActiveClient(senderName)
    val playerTempConnect = new PlayerTempConnect(name,senderName,host, actorRef)
    playerSender += (senderName -> playerTempConnect )
    playerName += (name -> playerTempConnect )
    existName(name)
  }

  //suprimir un cliente del diccionario Player
  def supPlayerBySender(senderName:String): Boolean ={
    playerName -= playerSender(senderName).name
    playerSender -= senderName
    if(existSender(senderName)) false
    else true
  }

  //suprimir un cliente del diccionario Player
  def supPlayerByName(name:String): Boolean ={
    playerSender -= playerName(name).senderName
    playerName -= name
    if(existName(name)) false
    else true
  }

  //retorna el nombre de un Player
  def playerNameBySender(senderName:String): String ={
    val player = playerSender.getOrElse(senderName, null)
    if (player == null)"null"
    else player.name
  }

  def playerSenderByName(name:String): String ={
    val player = playerName.getOrElse(name, null)
    if (player == null)"null"
    else player.senderName
  }

  //retorna el host de un Player
  def playerHostBySender(senderName:String): String ={
    val player = playerSender.getOrElse(senderName, null)
    if (player == null)"null"
    else player.host
  }

  def playerHostByName(name:String): String ={
    val player = playerName.getOrElse(name, null)
    if (player == null)"null"
    else player.host
  }

  //buscar un cliente al diccionario Player
  def playerActorRefBySender(senderName:String): ActorRef ={
    val player = playerSender.getOrElse(senderName, null)
    if (player == null) null
    else player.actorRef
  }

  def playerActorRefByName(name:String): ActorRef ={
    val player = playerName.getOrElse(name, null)
    if (player == null) null
    else player.actorRef
  }

  def existName(name:String): Boolean ={
    playerName.contains(name)
  }

  def existSender(SenderName:String): Boolean ={
    playerSender.contains(SenderName)
  }

  //obtener el conjunto de ActorRef en Player
  def allActorRef(): Iterable[ActorRef] ={
    playerSender.values.map(player => player.actorRef)
  }

  //obtener el conjunto de nombres en Player
  def allName(): Iterable[String] ={
    playerSender.values.map(player => player.name)
  }

  def allHost(): Iterable[String] ={
    playerSender.values.map(player => player.host)
  }

  def allSenderName(): Iterable[String] ={
    playerSender.keySet
  }


}
