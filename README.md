
# Conversation Conductor E2E Tests

This application is responsible to establie E2E test framework and conduct E2E test.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

  ###1. Clone E22 repository to your local machine
  
``` 
- Go to https://msazuredev@dev.azure.com/msazuredev/Nuance Enterprise/_git/conversation-conductor-e2e-tests 
- Git clone the repository either by HTTPS or SSH 
- Git checkout your own branch (i.e.: git checkout -b your branch name)
```

  ###2. Get access token in Azure

``` 
    - Go to https://dev.azure.com/msazuredev/_usersSettings/tokens
    - Click + New Token
      In the right panel, give a name for the token;
      give an expiration (longer the better);
      Scopes: check on "Custom defined";
      Work Items, Code, Build, Release, Test Management, Packaging: check on "Read & write";
      Click Create to create the token, save the token somewhere else.
```

###3. Setup IntelliJ
``` 
- Install IntelliJ if not already (Community version is fine)
- Open IntelliJ, go to File > Settings > Build, Execution, Deployment > Build Tools > Maven,
- Check "User settings file:" field to see where is settings.xml is specified.
- Go to the location of the settings.xml, create settings.xml if it doesn't exist there.
- Copy the content of attached settings.xml, and replace the two token field with your actual token generated in step 2.
```xml (copy from the line below) -->

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0https://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		<server>
			<id>entrd-lightsaber-common</id>
			<username>msazuredev</username>
			<password>token</password>
		</server>
		<server>
			<id>central</id>
			<username>msazuredev</username>
			<password>token</password>
		</server>
	</servers>
</settings>  
```

Now open the project you just cloned in step 1, it should install the dependencies automatically and open the project without authorization (401) error.
```


### End-to-end tests

Explain what these tests test and why

```
Give an example
```

### Unit tests

Explain what these test and why

```
Give examples
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

Documenting some of the main tools used to build this project, manage dependencies, etc will help users get more information if they are trying to understand or having difficulties getting the project up and running.

* Link to some dependency manager
* Link to some framework or build tool
* Link to some compiler, linting tool, bundler, etc

## Contributing

Please read our [CONTRIBUTING.md](CONTRIBUTING.md) which outlines all of our policies, procedures, and requirements for contributing to this project.

## Versioning and changelog

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](link-to-tags-or-other-release-location).

It is a good practice to keep `CHANGELOG.md` file in repository that can be updated as part of a pull request.

## Authors

List main authors of this project with a couple of words about their contribution.

Also insert a link to the `owners.txt` file if it exists as well as any other dashboard or other resources that lists all contributors to the project.

## License

This project is licensed under the < INSERT LICENSE NAME > - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
