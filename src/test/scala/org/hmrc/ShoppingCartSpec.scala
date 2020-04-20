package org.hmrc

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ShoppingCartSpec extends AnyFlatSpec with Matchers {

  val shoppingCart = new ShoppingCart()

  "shopping cart" should "calculate the price of items in the basket" in {
    val result = shoppingCart.checkout(List("Apple", "Apple", "Orange", "Apple"))

    result shouldEqual "£1.45"
  }

  it should "calculate the price of basket, given basket has only Apple" in {
    val result = shoppingCart.checkout(List("Apple"));

    result shouldEqual "£0.6"
  }

  it should "calculate the price of basket, given basket has only Orange" in {
    val result = shoppingCart.checkout(List("Orange"));

    result shouldEqual "£0.25"
  }

  it should "return 0 if basket is empty" in {
    val result = shoppingCart.checkout(List())
    result shouldEqual "£0.0"
  }

  it should "throw exception if basket has invalid item" in {
    val exception = intercept[IllegalArgumentException] {
      shoppingCart.checkout(List("Apple", "Banana", "Orange"))
    }
    exception.getMessage shouldBe "Item Banana is invalid"
  }

  "shopping cart with offers" should "apply buy one get one offer, given basket has 2 Apples" in {
    val result = shoppingCart.checkout(List("Apple", "Apple", "Orange")) // Apple cost -> 0.60, Orange cost -> 0.25

    result shouldEqual "£0.85"
  }

  it should "apply buy one get one offer, given basket has 5 Apples" in {
    val result = shoppingCart.checkout(List("Apple", "Apple", "Apple", "Apple", "Apple", "Orange")) // Apple cost -> 1.80, Orange cost -> 0.25

    result shouldEqual "£2.05"  // cost of 3 apples and 1 orange
  }

  it should "apply 3 for 2 offer, given basket has 3 Oranges" in {
    val result = shoppingCart.checkout(List("Apple", "Orange", "Orange", "Orange")) // Apple cost -> 0.60, Orange cost -> 0.50

    result shouldEqual "£1.1"  // cost of 3 apples and 1 orange
  }

  it should "apply 3 for 2 offer, given basket has 5 Oranges" in {
    val result = shoppingCart.checkout(List("Apple", "Orange", "Orange", "Orange",  "Orange",  "Orange")) // Apple cost -> 0.60, Orange cost -> 1.00

    result shouldEqual "£1.6"  // cost of 3 apples and 1 orange
  }

  it should "apply buy one get one offer and 3 for 2 offer, given basket has 5 Apples and 4 Oranges" in {
    val result = shoppingCart.checkout(List("Apple", "Apple", "Apple", "Apple", "Apple", "Orange", "Orange", "Orange", "Orange")) // Apple cost -> 1.80, Orange cost -> 0.75

    result shouldEqual "£2.55"  // cost of 3 apples and 1 orange
  }



}
