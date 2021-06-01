package pty.nfvd.app
package utils.api

trait RestfulJsonInfixOperation[T] {
	def into(t: T): T

	def defer(t: T): T

	def +>(t: T) = t.into(t)

	def <~(t: T) = t.defer(t)
}