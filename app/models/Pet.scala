package models

import scala.collection.concurrent.TrieMap

/**
  * Created by Edward on 23/2/17.
  */
case class Pet(
   id: Int,
   name: String,
   price: Double
)

trait TPetsRepo {
  def list(): List[Pet]
  def create(name: String, price: Double): Option[Pet]
  def update(id: Int, name:String, price: Double): Option[Pet]
  def delete(id: Int): Boolean
  def get(id: Int): Option[Pet]
}

object PetsRepo extends TPetsRepo {
  private var pets = new TrieMap[Int, Pet]()
  private var seq = 4


  pets.put(1, Pet(1, "small rabbit", 12))
  pets.put(2, Pet(2, "medium rabbit", 17))
  pets.put(3, Pet(3, "medium rabbit", 17))

  override def list(): List[Pet] = pets.values.toList

  override def create(name: String, price: Double): Option[Pet] = {
    val temp = Pet(seq, name, price)
    pets.put(seq, temp)
    seq += 1
    Some(temp)
  }

  override def update(id: Int, name: String, price: Double): Option[Pet] = {
    val temp = Pet(id, name, price)
    pets.replace(id, temp)
    Some(temp)
  }

  override def delete(id: Int): Boolean = {
    this.pets.remove(id).isDefined
  }

  override def get(id: Int): Option[Pet] = {
    this.pets.get(id)
  }
}