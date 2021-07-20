#! /usr/bin/env python3

import urllib.request
import webbrowser
from datetime import date
from requests import get

URL = "https://www.khaosod.co.th/breaking-news"
news_header = []
news_links = []

def run():
	fetch()
	show_news()
	open_website()

def show_news():
	today = date.today()
	today_date = today.strftime("%d %B %Y")
	print("######################################")
	print("# สรุปหัวข้อข่าวสดประจำวันที่", today_date, "#")
	print("######################################")
	line_number = 1
	for i in news_header:
		print(line_number, "-", i)
		line_number += 1

def fetch():
	web = get(URL)

	if (web.status_code != 200):
		print("โหลดข้อมูลไม่เสร็จ หรือการเชื่อมต่อมีปัญหา :", web.status_code)
		exit(0)	

	src = web.text

	start = 0
	end = 0
	while True:
		find = src.find("udblock__permalink", start)
		if find >= 0:
			start = find
			find = src.find(">", start)
			if find >= 0:
				start = find
				find = src.find("<", start)
				if find >= 0:
					end = find
					news_header.append(src[start + 1 : end])
					store_link(src, start - 100, end)
		else:
			break

def store_link(src, start, end):
	find = 0
	while True:
		find = src.find("href", start)	
		if find >= 0:
			start = find
			find = src.find("https", start)
			if find >= 0:
				start = find
				find = src.find("\"", start)
				if find >= 0:
					end = find
					news_links.append(src[start:end])
					break
		
def open_website():
	select_web = input("ต้องการเข้าเว็บไซต์ ? กรุณาเลือก (1 - 26) : ")
	if select_web.isnumeric():
		select_web = int(select_web)
		if (1 <= select_web <= 26):
			webbrowser.open(news_links[select_web - 1])

run()
