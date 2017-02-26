package controllers

import javax.inject.Inject

import play.api._
import play.api.mvc.{Action, BodyParser, Controller}
import play.api.libs.json._
import play.api.libs.json.Reads
import play.api.libs.functional.syntax._
import models.{TPetsRepo, _}

/**
  * Created by Edward on 23/2/17.
  */
class PetItemsController () extends Controller {
  private var repo = PetsRepo

  implicit val jsonToPet: Reads[Pet] = Json.reads[Pet]

  implicit val petToJson: Writes[Pet] = new Writes[Pet] {
    def writes(o: Pet):JsValue = Json.obj(
      "id" -> o.id,
      "name" -> o.name,
      "price" -> o.price
    )
  }

//  ??? what is the difference
//  implicit val placeWrites: Writes[Place] = (
//    (JsPath \ "name").write[String] and
//      (JsPath \ "location").write[Location]
//    )(unlift(Place.unapply))

  def list = Action{
    Ok(Json.toJson(repo.list()))
  }

  def create = Action(parse.json){ implicit request =>
    request.body.validate[Pet].fold(
      errs => {
        BadRequest
      },
      pet => {
        val temp = repo.create(pet.name, pet.price)
        Ok(Json.toJson(temp))
      }
    )
  }

  def get(id: Int) = Action{
    println("here")
    Ok(Json.toJson(repo.get(id)))
  }

  def update(id: Int) = Action(parse.json){ implicit request =>
    request.body.validate[Pet].fold(
      errs => {
        BadRequest
      },
      pet => {
        val temp = repo.update(id, pet.name, pet.price)
        println(repo.list())
        Ok(Json.toJson(temp))
      }
    )
  }

  def delete(id: Int) = Action{NotImplemented}
}
