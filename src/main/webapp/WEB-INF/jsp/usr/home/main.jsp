<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="MAIN" />
<%@ include file="../common/head.jspf"%>
 <div class="popup text-2xl">Popup Test</div>
<div class="layer-bg"></div>
<div class="layer">
	<div class="flex justify-between">
		<div class="text-2xl">POPUP</div>
		<div class="close-btn">
			<div></div>
			<div></div>
		</div>
	</div>
	<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Commodi repudiandae expedita vero nihil architecto
		nostrum blanditiis unde beatae! Id iusto ducimus minima. Libero quam voluptas velit eius consequatur delectus
		quisquam.</div>

</div>
<style>
/*팝업*/
.layer {
	display: none;
	width: 500px;
	height: 500px;
	background-color: black;
	color: white;
	border: 4px solid red;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translateX(-50%) translateY(-50%);
	z-index: 11;
	width: 500px;
}

.layer-bg {
	display: none;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, .4);
	position: absolute;
	top: 0;
	left: 0;
	z-index: 10;
}

.layer>div {
	padding: 20px;
}

.close-btn {
	width: 70px;
	height: 70px;
	border: 4px solid white;
	position: relative;
}

.close-btn>div {
	background-color: red;
	height: 10%;
	width: 100%;
	position: absolute;
	top: 30%;
	left: 50%;
	transition: transform .2s cubic-bezier(1, .48, 0, .96), top .2s;
	transform: translateX(-50%) translateY(-50%) rotate(0);
}

.close-btn>div:last-child {
	top: 70%;
}

.close-btn:hover>div {
	top: 50%;
	transform: translateX(-50%) translateY(-50%) rotate(45deg);
}

.close-btn:hover>div:last-child {
	transform: translateX(-50%) translateY(-50%) rotate(-45deg);
}
</style>
<Section>
				<h2 id="text">
						<span>It's time for a new</span>
						<br>
						Adventure
				</h2>

				<img src="https://user-images.githubusercontent.com/65358991/170092504-132fa547-5ced-40e5-ab64-ded61518fac2.png"
						id="bird1">
				<img src="https://user-images.githubusercontent.com/65358991/170092542-9747edcc-fb51-4e21-aaf5-a61119393618.png"
						id="bird2">
				<img src="https://user-images.githubusercontent.com/65358991/170092559-883fe071-eb4f-4610-8c8b-a037d061c617.png"
						id="forest">

				<a href="/usr/article/list" id="btn">Start</a>

				<img src="https://user-images.githubusercontent.com/65358991/170092605-eada6510-d556-45cc-b7fa-9e4d1d258d26.png"
						id="rocks">
				<img src="https://user-images.githubusercontent.com/65358991/170092616-5a70c4af-2eed-496f-bde9-b5fcc7142a31.png"
						id="water">
		</Section>

		<div class="sec">
				<h2>Parallax Scrolling Effects</h2>
				<p>
						Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
						magna aliqua. Est pellentesque elit ullamcorper dignissim cras tincidunt lobortis. Pellentesque elit eget gravida
						cum sociis. Id aliquet lectus proin nibh nisl condimentum id. Vestibulum lectus mauris ultrices eros in cursus
						turpis massa. Amet facilisis magna etiam tempor orci eu lobortis elementum. Proin libero nunc consequat interdum
						varius sit amet mattis vulputate. Lacus sed turpis tincidunt id. At augue eget arcu dictum. Tempor id eu nisl
						nunc. Sem fringilla ut morbi tincidunt. Bibendum est ultricies integer quis auctor. Viverra suspendisse potenti
						nullam ac tortor vitae purus faucibus. Tortor at risus viverra adipiscing at in tellus. Est lorem ipsum dolor sit
						amet consectetur. Dui faucibus in ornare quam viverra orci. Sapien faucibus et molestie ac feugiat sed lectus
						vestibulum mattis. Fringilla est ullamcorper eget nulla facilisi. At varius vel pharetra vel turpis.
						<br>

				</p>
		</div>

		<footer>
				<a href="http://www.freepik.com">Forest Graphics Designed by brgfx / Freepik</a>
		</footer>
		
		
		<script>
			let text = document.getElementById('text');
			let bird1 = document.getElementById('bird1');
			let bird2 = document.getElementById('bird2');
			let btn = document.getElementById('btn');
			let rocks = document.getElementById('rocks');
			let forest = document.getElementById('forest');
			let water = document.getElementById('water');
			let header = document.getElementById('header');

			window.addEventListener('scroll', function() {
				let value = window.scrollY;

				text.style.top = 50 + value * -.1 + '%';
				bird2.style.top = value * -1.5 + 'px';
				bird2.style.left = value * 2 + 'px';
				bird1.style.top = value * -1.5 + 'px';
				bird1.style.left = value * -5 + 'px';
				btn.style.marginTop = value * 1.5 + 'px';
				rocks.style.top = value * -.12 + 'px';
				forest.style.top = value * .25 + 'px';
				header.style.top = value * .5 + 'px';
			})
		</script>
		
		<style>
		section {
	position: relative;
	width: 100%;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
}

section::before {
	content: "";
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 100px;
	background: linear-gradient(to top, var(--primary), transparent);
	z-index: 10;
}

section img {
	position: absolute;
	top: 0px;
	left: 0;
	width: 100%;
	height: 100%;
	object-fit: cover;
	pointer-events: none;
}

section #text {
	position: absolute;
	color: var(--primary);
	font-size: 10vw;
	text-align: center;
	line-height: .55em;
	font-family: 'Rancho', cursive;
	transform: translatey(-50%);
}

section #text span {
	font-size: .20em;
	letter-spacing: 2px;
	font-weight: 400;
}

#btn {
	text-decoration: none;
	display: inline-block;
	padding: 8px 30px;
	background: #fff;
	color: var(--primary);
	font-size: 1.2em;
	font-weight: 500;
	letter-spacing: 2px;
	border-radius: 40px;
	transform: translatey(100px);
}

.sec {
	position: relative;
	padding: 100px;
	background: var(--primary);
}

.sec h2 {
	font-size: 3.5em;
	color: #fff;
	margin-bottom: 10px;
}

.sec p {
	font-size: 1em;
	color: #fff;
}

footer {
	position: relative;
	padding: 0px 100px;
	background: var(--primary);
}

footer a {
	text-decoration: none;
	color: #fff;
}
		</style>
<%@ include file="../common/foot.jspf"%>