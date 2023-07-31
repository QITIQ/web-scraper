const puppeteer = require('puppeteer'); 

const fs = require('fs').promises; 

//get the input from the java side
const argumentFromJava = process.argv[2];

//check if the input is a valid url
const isValidUrl = urlString=> {
	try { 
		return Boolean(new URL(urlString)); 
	}
	catch(e){ 
		return false; 
	}
}

//go to page
if(isValidUrl(argumentFromJava)){
	(async () => { 
		const browser = await puppeteer.launch(); 

		const page = await browser.newPage(); 

		await page.goto(argumentFromJava, { 

			waitUntil: 'networkidle0', 
		}); 

		const html = await page.content(); 

		//will copy the source code into a file for the java side to access
		await fs.writeFile('reactstorefront.html', html); 

		await browser.close(); 

	})();
}

// make a google search
else{
	(async () => {
		const browser = await puppeteer.launch(); 

		const page = await browser.newPage();

		await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36');


		await page.goto(`https://www.google.com/search?q=${argumentFromJava.replaceAll(' ','+')}`, { 

		waitUntil: 'networkidle0', 
	});

		//scrapped(for now) google search by going to google and actually typing and enter

		//await page.goto("https://www.google.com/");

		//await page.waitForNavigation({ waitUntil: 'networkidle0' });

		//await page.waitForSelector("#APjFqb");

		//await page.type("#APjFqb", argumentFromJava);

		//await page.keyboard.press("Enter");
		
		const html = await page.content(); 

		await fs.writeFile('reactstorefront.html', html); 

		await browser.close();
	})();
   
}
