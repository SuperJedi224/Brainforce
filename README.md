# Brainforce
A brainf*** module for ForceLang.

Install by adding the `brainforce` directory (found in the source folder) to the source folder for your ForceLang interpreter and recompiling.

Invoke the module object as a function to evaluate a bareword as brainf*** code, or invoke its `exec` method to evaluate a forcelang string. Both methods will both print (unless you disable printing) and return the output string.

You can enable and disable printing using the `setOutputEnabled` method, and enable and disable automatically resetting the interpreter using the `setAutoResetEnabled` method. Input should be given before calling the interpreter using the `offerInput` method.
