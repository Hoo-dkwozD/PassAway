// package sg.edu.sportsschool;

// import java.lang.reflect.Method;
// import java.util.concurrent.Executor;

// import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.task.SimpleAsyncTaskExecutor;
// import org.springframework.scheduling.annotation.AsyncConfigurer;
// import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// import sg.edu.sportsschool.Exceptions.InternalServerException;

// @Configuration
// public class SpringAsyncConfiguration implements AsyncConfigurer {

//     @Override
//     public Executor getAsyncExecutor() {
//         return new SimpleAsyncTaskExecutor();
//     }

//     @Override
//     public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//         return new AsyncUncaughtExceptionHandler() {
//             @Override
//             public void handleUncaughtException(Throwable ex, Method method, Object... params) {
//                 System.out.println("Exception caught in thread" + Thread.currentThread().getName());
//                 System.out.println("Exception message - " + ex.getMessage());
//                 System.out.println("Method name - " + method.getName());

//                 System.out.println("ex instanceof InternalServerException" + (ex instanceof InternalServerException));
//                 System.out.println("ex instanceof RuntimeException" + (ex instanceof RuntimeException));
//                 System.out.println("ex instanceof Exception" + (ex instanceof Exception));
//                 System.out.println(ex instanceof InternalServerException);
//                 if (ex instanceof InternalServerException) {
//                     throw new InternalServerException(ex.getMessage());
//                 }

//             }

//         };

//     }
// }
