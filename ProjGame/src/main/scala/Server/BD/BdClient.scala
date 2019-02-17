package Server.BD

import akka.actor.ActorRef

case object BdClient {
  /*
  la redundancia de datos en los dos diccionarios ClientIdentities
  es para mejorar el rendimiento de busqueda
  */
  //diccionario de clientes activos senderName -> ActorRef
  private val ActiveClients = scala.collection.mutable.HashMap.empty[String, ActorRef]
  //diccionario de identidades de clientes userName -> senderName
  private val ClientIdentities = scala.collection.mutable.HashMap.empty[String, String]
  //diccionario de identidades de clientes senderName -> userName
  private val ClientIdentities2 = scala.collection.mutable.HashMap.empty[String, String]

  //agregar un cliente al diccionario ActiveClients
  def addActiveClient(name:String, senderName:ActorRef): Unit ={
    ActiveClients += (name -> senderName)
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

  //verificar si un nombre de usuario esta en el diccionario
  def nameExistsIdentities(name:String): Boolean ={
    if (ClientIdentities.getOrElse(name, null)==null) false
    else true
  }

  //verificar si un senderName(identificador de usuario) esta en el diccionario
  def senderExistsIdentities(senderName:String): Boolean ={
    if (ClientIdentities2.getOrElse(senderName, null)==null) false
    else true
  }

  //agregar un usuario en los dos diccionarios redundantes ClientIdentities
  def addClientIdentities(name:String, senderName:String): Unit ={
    ClientIdentities += (name -> senderName)
    ClientIdentities2 += (senderName -> name)
  }

  //suprimir un usuario en los dos diccionarios ClientIdentities a partir del nombre
  def supClientIdentities(name:String): Unit ={
    ClientIdentities2 -= findSenderIdentities(name)
    ClientIdentities -= name
  }

  //suprimir un usuario en los dos diccionarios ClientIdentities a partir del senderName(identificador de usuario)
  def supClientIdentities2(senderName:String): Unit ={
    ClientIdentities -= findNameIdentities(senderName)
    ClientIdentities -= senderName
  }

  //recibe el name de un usuario y retorna el senderName
  def findSenderIdentities(name:String): String ={
    val senderName = ClientIdentities.getOrElse(name, null)
    if(senderName == null) "null"
    else senderName
  }

  //recibe el senderName de un usuario y retorna el name
  def findNameIdentities(senderName:String): String ={
    val name = ClientIdentities2.getOrElse(senderName, null)
    if(name == null) "null"
    else name
  }
}
