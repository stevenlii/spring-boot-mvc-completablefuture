package net.andreaskluth;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.springframework.stereotype.Component;

@Component
public class SomeService {

	@AsyncTimed
	public CompletableFuture<String> getMessage() {

		return CompletableFuture.supplyAsync(() -> {
			heavyWork();
			return "hello async world";
		}, ForkJoinPool.commonPool());
	}
	private void heavyWork() {
		try {
			Thread.sleep(42);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}
	public String getMessage2() {
		CompletableFuture<String> messResult =  CompletableFuture.supplyAsync(() -> {
			return heavyWork2();
		}, ForkJoinPool.commonPool());
		return messResult.join();
	}
	public String heavyWork2() {
	  try {
		  Thread.sleep(42);
		  return getMessage3().join();
	  }
	  catch (InterruptedException e) {
		  Thread.interrupted();
	  }
	return null;
  }

	private String convertToB(String a) {
		return "hello async2 world";
	}
	@AsyncTimed
	public CompletableFuture<String> getMessage3() {
		return CompletableFuture.supplyAsync(() -> {
			heavyWork();
			return "hello async world";
		}, ForkJoinPool.commonPool()).thenApply(a -> convertToB(a));
	}
}