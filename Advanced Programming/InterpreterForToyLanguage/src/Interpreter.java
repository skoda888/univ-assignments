import Controller.Controller_Interpreter;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.RepositoryMemoryBased_Interpreter;
import View.Command.ExitCommand;
import View.Command.RunAllStepsCommand;
/*import View.Interpreter_View;*/
import View.ViewTextMenuBased;

import java.util.Deque;
import java.util.LinkedList;

public class Interpreter {
    public static void main(String[] args) {
        // Create statements
        IStatement ex1 =
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("v",
                                        new ValueExpression(new IntValue(2))),
                                new PrintStatement(new VariableExpression("v"))
                        )
                );

        IStatement ex2 =
                new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntValue(2)), new
                                        ArithmeticExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                        new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new
                                                ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement ex3 =
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                        new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                        new CompoundStatement(new IfElseStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new
                                                IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                                VariableExpression("v"))))));

        IStatement ex4 =
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                                new CompoundStatement(new OpenFileStatement(new VariableExpression("varf")),
                                        new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                                new CloseFileStatement(new VariableExpression("varf"))
                                                                        ))))))));

        IStatement ex5 = new PrintStatement(new RelationalExpression(">", new ValueExpression(new IntValue(5)), new ValueExpression(new IntValue(3))));

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v))
        IStatement ex6 =
            new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                        new CompoundStatement (new NewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                    new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new HeapReadingExpression(new VariableExpression("v")))
                                ))));

        // int v; v=4; (while (v>0) print(v);v=v-1);
        IStatement ex7 =
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                                    new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                    new AssignmentStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                            )
                                    )
                            )
                        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex8 =
                new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                        new CompoundStatement (new NewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                        new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))))

                )))));

        IStatement ex9 =
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                        new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                                new CompoundStatement(new ForkStatement(
                                                        new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                            new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                                                                    )
                                                                )
                                                        )
                                                ),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                            new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                                                ))))));

        // Perform type checking
        try {
            ex1.checkTypes(new ADTDictionary<>());
            ex2.checkTypes(new ADTDictionary<>());
            ex3.checkTypes(new ADTDictionary<>());
            ex4.checkTypes(new ADTDictionary<>());
            ex5.checkTypes(new ADTDictionary<>());
            ex6.checkTypes(new ADTDictionary<>());
            ex7.checkTypes(new ADTDictionary<>());
            ex8.checkTypes(new ADTDictionary<>());
            ex9.checkTypes(new ADTDictionary<>());

            System.out.println("\nType checking performed.\n");
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        // Create program states
        ProgramState programState1 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex1);
        ProgramState programState2 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex2);
        ProgramState programState3 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex3);
        ProgramState programState4 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex4);
        ProgramState programState5 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex5);
        ProgramState programState6 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex6);
        ProgramState programState7 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex7);
        ProgramState programState8 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex8);
        ProgramState programState9 = new ProgramState(new ADTStack<>(), new ADTDictionary<>(), new ADTList<>(), new ADTDictionary<>(), new ADTDictionaryForHeap(), ex9);
        // Create controllers
        try {
            Controller_Interpreter controller_1 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState1, "log_ex1.txt"));
            Controller_Interpreter controller_2 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState2, "log_ex2.txt"));
            Controller_Interpreter controller_3 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState3, "log_ex3.txt"));
            Controller_Interpreter controller_4 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState4, "log_ex4.txt"));
            Controller_Interpreter controller_5 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState5, "log_ex5.txt"));
            Controller_Interpreter controller_6 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState6, "log_ex6.txt"));
            Controller_Interpreter controller_7 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState7, "log_ex7.txt"));
            Controller_Interpreter controller_8 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState8, "log_ex8.txt"));
            Controller_Interpreter controller_9 = new Controller_Interpreter(new RepositoryMemoryBased_Interpreter(programState9, "log_ex9.txt"));


            // Run view
            ViewTextMenuBased viewTextMenuBased = new ViewTextMenuBased();
            viewTextMenuBased.addCommand(new ExitCommand("exit", "exit"));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex1", "allstep ex1", controller_1));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex2", "allstep ex2", controller_2));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex3", "allstep ex3", controller_3));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex4", "allstep ex4", controller_4));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex5", "allstep ex5", controller_5));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex6", "allstep ex6", controller_6));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex7", "allstep ex7", controller_7));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex8", "allstep ex8", controller_8));
            viewTextMenuBased.addCommand(new RunAllStepsCommand("allstep ex9", "allstep ex9", controller_9));
            viewTextMenuBased.show();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }

    }
}
