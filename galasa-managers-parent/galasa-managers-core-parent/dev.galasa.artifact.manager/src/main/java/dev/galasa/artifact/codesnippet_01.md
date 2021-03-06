<details><summary>Create the Artifact Manager</summary>

The following snippet shows the minimum code that is required to request the Artifact Manager in a test:

```
@ArtifactManager
public IArtifactManager artifacts;
```

The code creates an interface to the Artifact Manager. The Artifact Manager allows the test to stream resources from the same bundle as the test and can also be used to substitute parameters into a skeleton file.
</details>

<details><summary>Create a reference to the bundles resources</summary>

```
IBundleResources resources = artifacts.getBundleResources(this.getClass());
```

The IBundleResources object now references the list of resources that are held within the test bundle under /src/main/resources/  These resources can now be accessed directly by the test code.
</details>

<details><summary>Retrieving the content of a file as a string</summary> 

The simplest thing that a test might want to do is retrieve the content of a file as a string.  First, an input stream to the file location is constructed and then a helper method is invoked to stream the content as a string.  However, if you wanted to read from the input stream yourself then that option is available to you.

```
InputStream is = resources.retrieveFile("input/hobbit.txt");
String textContext = resources.streamAsString(is);
```
</details>

<details><summary>Substituting Variables into a file</summary>

As you read a file from the local bundle you might want to substitute values into the file before retrieving it.  This is called within Galasa as retrieving a skeleton file from the bundle.  The first step is to create a HashMap that will contain the name of the variables you want to be substituted in the target file as well as the value that should be used.

```
HashMap<String, Object> parameters = new HashMap<String, Object>();
parameters.put("ACCOUNT_NUMBER", "123456789");
parameters.put("AMOUNT", "50.05");
```

In this case we are adding two items to the HashMap, setting the substitute values for both ACCOUNT_NUMBER and AMOUNT.  This HashMap is then passed to the retrieveSkeletonFile method.

```
InputStream is = resources.retrieveSkeletonFile("input/hobbit.txt", parameters);
String textContext = resources.streamAsString(is);
```

When the file is now retrieved from the local bundle all references to ++ACCOUNT_NUMBER++ and ++AMOUNT++ will be substituted for "123456789" and "50.05" respectively.  Note that these substitutions are for the instance of the test running.  The actual copy of the file in the bundle is unchanged and cannot affect any other instances of the test running at the same time.
</details>
